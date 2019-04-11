# Spring Data REST

__Do__ use Spring Data REST.

__Why?__ It uses standards and REST best practices.

* REST level 3 - HATEOAS
* HAL 
* ALPS, JSON Schema
* RFC-5988 Web Linking, RFC-6570 URI Template
* Affordances (future)
* HAL-FORMS (future)
* Collection+JSON (future)
* UBER (future)

__Why?__ Improved developer experience for the author.

* Lots of [cool features](https://github.com/marklishman/colors-microservices/blob/master/purple/README.md) for free
* Reduced boilerplate code, less moving parts, less maintenance
* It just works :-)

__Why?__ Improved developer experience for the client.

* Discoverability (hypermedia, links)
* Top level API to get started
* HAL Browser
* Metadata (with descriptions)
* Documentation

__Why?__ It can make client code easier to develop and maintain.

* Hypermedia, Links
* Traverson
* Maintainability
* Metadata
* HAL-FORMS (future)

In my opinion, the benefits of Spring Data REST outweigh the cost of implementing 
HATEOAS in the other non Spring Data REST services.


__Avoid__ Spring Data REST if HATEOAS is not an option.

__Why?__ It can't be switched off in Spring Data REST.


__Avoid__ Spring Data REST if no services are data-centric (CRUD)

__Why?__ Spring Data REST adds little or no value.


# Naming

__Do__ add the `Entity` suffix to entity classes. `CountryEntity`

__Why?__ It easily identifies entity classes and avoids name classes with other model objects.

__Do__ add the `Projection` suffix to projection classes. `ItemListProjection`

__Why?__ It easily identifies projection classes and avoids name classes with other model objects.


# Access

__Do__ use the `ANOTATION` [detection strategy](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#getting-started.setting-repository-detection-strategy)

__Why?__ It is the most explicit and restrictive of the detection strategies which should help to avoid accidental 
exposure of repository methods.

__Do__ only implement the repository methods that are needed.

__Why?__ It stops unwanted REST endpoints from being exposed.

__Do__ use `RestResource(exported = false)` to restrict access to methods.

__Why?__ It stops unwanted REST endpoints from being exposed.

__Do__ use Spring Security on repository methods.

__Why?__ It controls who can use the REST endpoint.

# Example

[User MIcroservice](https://github.com/marklishman/user-microservice)
