# Cyan

# Project Features

* WebClient
* RestTemplate
* Traverson
* Feign

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

If the resource is in HAL format first we need to define a couple of `ParameterizedTypeReference`s

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
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Person.class)
            .count();

    final Mono<Long> countryCountMono = greenWebClient
            .get()
            .uri("green/countries")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(Country.class)
            .count();

    final Mono<Long> userCountMono = whiteWebClient
            .get()
            .uri("controller/users")
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .bodyToFlux(User.class)
            .count();

    return Flux.mergeSequential(peopleCountMono, countryCountMono, userCountMono)
            .collectList()
            .map(list -> Statistics.newInstance(list.get(0), list.get(1), list.get(2)))
            .block();
}
~~~
