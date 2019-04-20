# Cyan

# Project Features

* WebClient
* RestTemplate
* Traverson
* Feign

---

# Set Up

* Start the `ref-db` database
* Start the `green`, `purple` and `white` services
* Start this service (`cyan`)
* Run the HTTP scripts in the `/scripts` directory

---

# Service Classes

* `CountriesService` - WebClient, synchronous
* `GroupService` - Traverson
* `PeopleFeignClient` - Feign
* `PeopleService` - RestTemplate, Feign
* `StatisticsService` - WebClient, synchronous
* `UserService` - WebClient, WebFlux
* `WebClientApi` - WebClient, WebFlux

---

# WebClient

> Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.

Create the `WebClient` bean

~~~java
@Bean
public WebClient greenWebClient() {
    return WebClient
            .builder()
            .baseUrl("http://localhost:8021")
            .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
            .build();
}
~~~

and get a resource collection

~~~java
public List<Country> getCountries() {
    return greenWebClient
            .get()
            .uri("green/countries")
            .retrieve()
            .bodyToFlux(Country.class)
            .collectList()
            .block();
}
~~~

or a single resource item.

~~~java
public Country getCountry(final Long id) {
    return greenWebClient
            .get()
            .uri("green/countries/{id}", id)
            .retrieve()
            .bodyToMono(Country.class)
            .block();
}
~~~

## HAL

If the resource is in HAL format we need to configure a Jackson module to marshal the HAL resource,

~~~java
@Bean
public WebClient greenHalWebClient() {

    final ObjectMapper mapper = new ObjectMapper();
    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    mapper.registerModule(new Jackson2HalModule());

    final ExchangeStrategies strategies = ExchangeStrategies
            .builder()
            .codecs(clientDefaultCodecsConfigurer -> {
                clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper(), MediaType.APPLICATION_JSON));
                clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON, MediaTypes.HAL_JSON));
            }).build();

    return WebClient
            .builder()
            .baseUrl("http://localhost:8021")
            .defaultHeader(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE)
            .exchangeStrategies(strategies)
            .build();
}
~~~

define a couple of `ParameterizedTypeReference`s

~~~java
    private static final ParameterizedTypeReference<Resource<Country>> COUNTRY_TYPE_REF = 
        new ParameterizedTypeReference<>() {};
        
    private static final ParameterizedTypeReference<Resources<Resource<Country>>> COUNTRIES_TYPE_REF = 
        new ParameterizedTypeReference<>() {};
~~~

and include a utility class to extract a list of objects from `Resources`.

~~~java
public class ResourceUtils {

    private ResourceUtils() { }

    public static <T> List<T> getContent(Resources<Resource<T>> resources) {
        return resources
                .getContent()
                .stream()
                .map(Resource::getContent)
                .collect(Collectors.toList());
    }

}
~~~

Then we can get a collection of HAL resources and extract the content as a list

~~~java
public List<Country> getCountriesAsHal() {
    return greenHalWebClient
            .get()
            .uri("green/countries")
            .retrieve()
            .bodyToMono(COUNTRIES_TYPE_REF)
            .map(ResourceUtils::getContent)
            .block();
}
~~~

or extract the content directly from a single resource item. 

~~~java
public Country getCountryAsHal(final Long id) {
    return greenHalWebClient
            .get()
            .uri("green/countries/{id}", id)
            .retrieve()
            .bodyToMono(COUNTRY_TYPE_REF)
            .map(Resource::getContent)
            .block();
}
~~~

Note that we are using `WebClient` in synchronous mode by blocking at the end for the result.

## Combining Results

When making multiple calls it is more efficient to avoid blocking on each response 
and instead wait for the combined result using `merge`, `mergeSequential` or `zip` etc.

~~~java
public Statistics getStats() {
    final Mono<Long> peopleCountMono = greenWebClient
            .get()
            .uri("green/people")
            .retrieve()
            .bodyToFlux(Person.class)
            .count();

    final Mono<Long> countryCountMono = greenWebClient
            .get()
            .uri("green/countries")
            .retrieve()
            .bodyToFlux(Country.class)
            .count();

    final Mono<Long> userCountMono = whiteWebClient
            .get()
            .uri("controller/users")
            .retrieve()
            .bodyToFlux(User.class)
            .count();

    return Flux.mergeSequential(peopleCountMono, countryCountMono, userCountMono)
            .collectList()
            .map(list -> Statistics.newInstance(list.get(0), list.get(1), list.get(2)))
            .block();
}
~~~

# WebFlux

We can of course use `WebClient` with WebFlux. 

For example, a get

~~~java
final Flux<User> users = whiteWebClient
        .get()
        .uri("/controller/users")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(User.class);
~~~

a post

~~~java
return whiteWebClient
        .post()
        .uri("/controller/users")
        .body(Mono.just(new User("four", "user_four", "four@email.com", "067856469", "www.four.com")), User.class)
        .exchange()
        .flatMap(response -> response.toEntity(User.class))
        .doOnSuccess(o -> System.out.println("**********POST " + o));
~~~

or an event stream.

~~~java
whiteWebClient
        .get()
        .uri("/controller/users/events")
        .accept(MediaType.TEXT_EVENT_STREAM)
        .retrieve()
        .bodyToFlux(UserEvent.class)
        .log()
        .subscribe(userEvent -> latestUserEvent = userEvent);
~~~

---

# RestTemplate

To use `RestTemplate` to retrieve a HAL resource add include `@EnableHypermediaSupport` annotation.
This will register the Jackson module to marshal the resource model classes into the appropriate representation.

~~~java
@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableFeignClients
public class CyanApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyanApplication.class, args);
    }
}
~~~

create a bean (necessary to use the config created with the `@EnableHypermediaSupport` annotation above)

~~~java
@Bean
public RestTemplate greenRestTemplate() {
    return new RestTemplateBuilder()
            .rootUri("http://localhost:8021/green")
            .build();
}
~~~

and use the `exchange` method with a `ParameterizedTypeReference`.

~~~java
public List<Person> getPeople() {
    LOGGER.info("Get People with RestTemplate and HAL");

    final ParameterizedTypeReference<Resources<Resource<Person>>> peopleResourceTypeReference =
            new ParameterizedTypeReference<>() {};

    final Resources<Resource<Person>> peopleResources = greenRestTemplate
            .exchange("/people", HttpMethod.GET, null, peopleResourceTypeReference)
            .getBody();

    return peopleResources
            .getContent()
            .stream()
            .map(Resource::getContent)
            .collect(Collectors.toList());
}
~~~

---

# Feign

Feign is a declarative web service client.

Enable Feign with the `@EnableFeignClients` annotation

~~~java
@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableFeignClients
public class CyanApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyanApplication.class, args);
    }
}
~~~

and create the client interface.

~~~java
@FeignClient(name = "people", url = "http://localhost:8021/green/people")
public interface PeopleFeignClient {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<Person> getPeople();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    Person getPerson(@PathVariable("id") final Long id);
}
~~~

To use the Feign client simply call the methods on the interface.

~~~java
public List<Person> getPeopleWithFeign() {
    return peopleFeignClient.getPeople();
}

public Person getPersonWithFeign(final Long id) {
    return peopleFeignClient.getPerson(id);
}
~~~ 

---

# Traverson

Traverson is an API for client side service traversal.

Create the bean

~~~java
@Bean
public Traverson greenTraverson() {
    return new Traverson(getUri("http://localhost:8061/purple/api"), MediaTypes.HAL_JSON);
}
~~~

and follow the links

~~~java
public Collection<Group> getGroups() {
    final ParameterizedTypeReference<Resources<Group>> groupsResourceTypeReference =
            new ParameterizedTypeReference<>() {};

    final Resources<Group> GroupsResource = greenTraverson
            .follow("groups")
            .toObject(groupsResourceTypeReference);

    return GroupsResource.getContent();
}
~~~

or use JSONPath.

~~~java    
public Group getGroup(final Long pos) {
    final ParameterizedTypeReference<Resource<Group>> groupResourceTypeReference =
            new ParameterizedTypeReference<>() {};

    final String rel = String.format("$._embedded.groups[%s]._links.self.href", pos);

    final Resource<Group> groupResource = greenTraverson
            .follow(rel)
            .toObject(groupResourceTypeReference);

    return groupResource.getContent();
}
~~~
