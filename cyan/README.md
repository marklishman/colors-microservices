# Cyan

# Project Features

* WebClient
* RestTemplate
* Traverson
* Feign

---

# Getting Started

* Start the `ref-db` database
* Start the `green`, `purple` and `white` services
* Start this service (`cyan`)
* Run the HTTP scripts in the `/scripts/http` directory

---

# WebClient

> Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.

To start with se will use `WebClient` to perform a synchronous request.

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
public List<User> getUsers() {
    return greenWebClient
            .get()
            .uri("green/users")
            .retrieve()
            .bodyToFlux(User.class)
            .collectList()
            .block();
}
~~~

or a single resource item.

~~~java
public User getUser(final Long id) {
    LOGGER.info("Get User {} with WebClient", id);
    return greenWebClient
            .get()
            .uri("green/users/{id}", id)
            .retrieve()
            .bodyToMono(User.class)
            .block();
}
~~~

Note that we use the `block()` method here to wait for the response.

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
private static final ParameterizedTypeReference<Resource<User>> USER_TYPE_REF = 
    new ParameterizedTypeReference<>() {};
private static final ParameterizedTypeReference<Resources<Resource<User>>> USERS_TYPE_REF = 
    new ParameterizedTypeReference<>() {};
~~~

and include a utility class to extract a list of objects from `Resources`.

~~~java
public final class ClientUtils {

    private ClientUtils() { }

    public static <T> List<T> getResourcesContent(Resources<Resource<T>> resources) {
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
public List<User> getUsersAsHal() {
    return greenHalWebClient
            .get()
            .uri("green/users")
            .retrieve()
            .bodyToMono(USERS_TYPE_REF)
            .map(ClientUtils::getResourcesContent)
            .block();
}
~~~

or extract the content directly from a single resource item. 

~~~java
public User getUserAsHal(final Long id) {
    return greenHalWebClient
            .get()
            .uri("green/users/{id}", id)
            .retrieve()
            .bodyToMono(USER_TYPE_REF)
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

    final Mono<Long> userCountMono = greenWebClient
            .get()
            .uri("green/users")
            .retrieve()
            .bodyToFlux(User.class)
            .count();

    final Mono<Long> countryCountMono = purpleHalWebClient
            .get()
            .uri(uriBuilder -> uriBuilder
                    .path("purple/api/countries")
                    .queryParam("size", Integer.MAX_VALUE)
                    .build()
            )
            .retrieve()
            .bodyToMono(Resources.class)
            .map(Resources::getContent)
            .map(Collection::size)
            .map(Long::valueOf);

    final Mono<Long> employeeCountMono = whiteWebClient
            .get()
            .uri("controller/employees")
            .retrieve()
            .bodyToFlux(Employee.class)
            .count();

    return Flux.mergeSequential(userCountMono, countryCountMono, employeeCountMono)
            .collectList()
            .map(list -> Statistics.newInstance(list.get(0), list.get(1), list.get(2)))
            .block();
}
~~~

## Updates

We can use WebClient to perform updates too.

Post

~~~java
public User createUser(final User user) {
    return greenWebClient
            .post()
            .uri("green/users")
            .syncBody(user)
            .retrieve()
            .bodyToMono(User.class)
            .block();
}
~~~

Put

~~~java
public User updateUser(final Long id, final User user) {
    final User updatedUser = user.cloneWithNewId(id);
    return greenWebClient
            .put()
            .uri("green/users/{id}", id)
            .syncBody(updatedUser)
            .retrieve()
            .bodyToMono(User.class)
            .block();
}
~~~

Delete

~~~java
public void deleteUser(final Long id) {
    greenWebClient
            .delete()
            .uri("green/users/{id}", id)
            .retrieve()
            .bodyToMono(Void.class)
            .block();
}
~~~

# WebFlux

We can of course use `WebClient` with WebFlux. 

For example, a get

~~~java
final Flux<Employee> employees = whiteWebClient
        .get()
        .uri("/controller/employees")
        .accept(MediaType.APPLICATION_JSON)
        .retrieve()
        .bodyToFlux(Employee.class);
~~~

or a post.

~~~java
private Mono<ResponseEntity<Employee>> postNewEmployee() {
    return whiteWebClient
            .post()
            .uri("/controller/employees")
            .body(Mono.just(new Employee("four", "employee_four", "four@email.com", "067856469")), Employee.class)
            .exchange()
            .flatMap(response -> response.toEntity(Employee.class))
            .doOnSuccess(o -> System.out.println("**********POST " + o));
}
~~~

## Event Streams

Suppose this stream of events is produced on the server.

~~~java
@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<EmployeeEvent> getEmployeeEvents() {
    return Flux.interval(Duration.ofSeconds(2))
            .map(val ->
                    new EmployeeEvent(val, "Employee Event " + val)
            );
}
~~~

We can subscribe to the stream like this

~~~java
private Disposable disposable;
private EmployeeEvent latestEmployeeEvent;
~~~

~~~java
disposable = whiteWebClient
        .get()
        .uri("/controller/employees/events")
        .accept(MediaType.TEXT_EVENT_STREAM)
        .retrieve()
        .bodyToFlux(EmployeeEvent.class)
        .log()
        .subscribe(employeeEvent -> latestEmployeeEvent = employeeEvent);
~~~

and cancel the subscription when we are done.

~~~java
disposable.dispose();
~~~

---

# RestTemplate

Create a `RestTemplate` bean

~~~java
@Bean
public RestTemplate greenRestTemplate() {
    return new RestTemplateBuilder()
            .rootUri("http://localhost:8021")
            .build();
}
~~~

and use the `getForObject` method to retrieve the resource.

~~~java
public User getUser(final Long id) {
    return greenRestTemplate
        .getForObject("/green/users/{id}", User.class, id);
}
~~~


To use `RestTemplate` to retrieve a HAL resource include the `@EnableHypermediaSupport` annotation.
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

Create a `RestTemplate` bean (this necessary to use the config created by the `@EnableHypermediaSupport` annotation above)

~~~java
@Bean
public RestTemplate purpleRestTemplate() {
    return new RestTemplateBuilder()
            .rootUri("http://localhost:8061/purple/api")
            .build();
}
~~~

and use the `exchange` method with a `ParameterizedTypeReference`.

~~~java
public Person getPerson(final Long id) {
    final ParameterizedTypeReference<Resource<Person>> personResourceTypeReference =
            new ParameterizedTypeReference<>() {};

    final Resource<Person> personResource = purpleRestTemplate
            .exchange("/people/{id}", HttpMethod.GET, null, personResourceTypeReference, id)
            .getBody();

    return personResource.getContent();
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
@FeignClient(name = "users", url = "http://localhost:8021/green/users")
public interface FeignUsersClient {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getUsers();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    User getUser(@PathVariable("id") final Long id);
}
~~~

To use the Feign client simply call the methods on the interface.

~~~java
@Service
public final class FeignUsersService {

    private final FeignUsersClient feignUsersClient;

    public FeignUsersService(final FeignUsersClient feignUsersClient) {
        this.feignUsersClient = feignUsersClient;
    }

    public List<User> getUsers() {
        return feignUsersClient.getUsers();
    }

    public User getUser(final Long id) {
        return feignUsersClient.getUser(id);
    }
}
~~~ 

---

# Traverson

Traverson is an API for client side service traversal.

Create the bean

~~~java
@Bean
public Traverson purpleTraverson() {
    return new Traverson(getUri("http://localhost:8061/purple/api"), MediaTypes.HAL_JSON);
}
~~~

and follow the links

~~~java
public Collection<Group> getGroups() {
    final ParameterizedTypeReference<Resources<Group>> groupsResourceTypeReference =
            new ParameterizedTypeReference<>() {};

    final Resources<Group> GroupsResource = purpleTraverson
            .follow("groups")
            .toObject(groupsResourceTypeReference);

    return GroupsResource.getContent();
}
~~~

or use JSONPath.

~~~java   
public Group getGroup(final Long pos) {
    LOGGER.info("Get Group at position {} with Traverson as HAL", pos);

    final ParameterizedTypeReference<Resource<Group>> groupResourceTypeReference =
            new ParameterizedTypeReference<>() {};

    final String rel = String.format("$._embedded.groups[%s]._links.self.href", pos);

    final Resource<Group> groupResource = purpleTraverson
            .follow("groups", rel)
            .toObject(groupResourceTypeReference);

    return groupResource.getContent();
}
~~~

---

# Adding Extra JSON Data

Adding data to the representation of a resource will not break existing clients if 
these are correctly implemented.

For example, the green service returns this JSON for a User.

~~~json
{
  "id": 6,
  "firstName": "Dennis",
  "lastName": "Schulist",
  "userName": "Leopoldo_Corkery",
  "email": "Karley_Dach@jasper.info",
  "phoneNumber": "1-477-935-8478 x6430",
  "age": 19,
  "website": "ola.org",
  "fullName": "Dennis Schulist",
  "adult": true
}
~~~

However the client uses this static factory method to create the object.

~~~java
@JsonCreator
public static User jsonCreator(@JsonProperty("id") final Long id,
                               @JsonProperty("firstName") final String firstName,
                               @JsonProperty("lastName") final String lastName,
                               @JsonProperty("userName") final String userName,
                               @JsonProperty("email") final String email,
                               @JsonProperty("phoneNumber") final String phoneNumber,
                               @JsonProperty("age") final Integer age,
                               @JsonProperty("website") final String website) {
    return newInstance(id, firstName, lastName, userName, email, phoneNumber, age, website);
}
~~~

Note that the `fullName` and `adult` properties are ignored.
