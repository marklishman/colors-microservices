# Cyan

# Project Features

* WebClient
* RestTemplate
* Traverson
* Feign

---

# WebClient

> Non-blocking, reactive client to perform HTTP requests, exposing a fluent, reactive API over underlying HTTP client libraries such as Reactor Netty.

`WebClient` Bean
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

Get a resource collection

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

Get a resource item

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

To GET the resource in HAL formal first we need to define a couple of `ParameterizedTypeReference`s

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

Then we can get a collection of HAL resources

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

or a single HAL resource 

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
