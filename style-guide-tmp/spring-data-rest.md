# Spring Data REST

__Do__ use Spring Data REST.

__Why?__ Standards and Best Practice

* REST level 3 - HATEOAS
* HAL 
* ALPS
* Affordances (future)
* HAL-Forms (future)
* UBER (future)

__Why?__ Improved Developer experience for the author

* Lots of cool features for free
* Reduced boilerplate code, less moving parts, less maintenance
* It just works :-)

__Why?__ Improved Developer experience for the client

* Discoverability (Hypermedia, links)
* Top level API to get started
* Metadata (with descriptions)
* HAL Browser
* Client usage (eg Links, HAL-FORMS)
* Documentation

__Why?__ Client Code

* Hypermedia, Links
* Traverson
* Maintainability
* HAL-Forms (future)
* Metadata

In my opinion, the benefits of Spring Data REST outweigh the cost of implementing 
HATEOAS on non Spring Data REST services.


__Avoid__ Spring Data REST if HATEOAS is not an option.

__Why?__ It can't be switched off


__Avoid__ Spring Data REST if no services are data-centric (CRUD)

__Why?__ Spring Data REST adds little value.


# Naming

__Do__ add the `Entity` suffix to entity classes. `CountryEntity`

__Why?__ Easily identifies entity classes and avoids name classes with other model objects.

__Do__ add the `Projection` suffix to projection classes. `ItemListProjection`

__Why?__ Easily identifies projection classes and avoids name classes with other model objects.


# Access

__Do__ use the `ANOTATION` [detection strategy](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#getting-started.setting-repository-detection-strategy)

__Why?__ It is the most explicit and restrictive of the detection strategies which should help to avoid accidental exposure of repos.

__Do__ only implement the repository methods that are needed.

__Why?__ It stops unwanted REST endpoints from being exposed.

__Do__ use `@RestResource` to restrict access to methods.

__Why?__ It stops unwanted REST endpoints from being exposed.

__Do__ use Spring Security on repository methods.

__Why?__ It controls who can use the REST endpoint.

# Example

[User MIcroservice](https://github.com/marklishman/user-microservice)
