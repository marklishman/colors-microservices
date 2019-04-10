# Spring Data REST

__Do__ use Spring Data REST if at least some of the microservices are data-centric (CRUD).

__Why?__ Features
 
* Lots of cool features for free (see below)
* Reduces boiler plate code
* It just works really well :-)

__Why?__ Standards

* REST level 3 - HATEOAS
* HAL (HAL-Forms, USER etc)
* ALPS

__Why?__ Developer experience

* Discoverability (Hypermedia, links)
* Top level API to get started
* Metadata (with descriptions)
* HAL Browser
* Reduced boilerplate code, less maintenance
* Client usage (eg Links, HAL-FORMS)
* Documentation

Benefits of Spring Data REST outweigh cost of implementing HATEOAS on non Spring Data REST services.

__Why?__ Client Code

* Hypermedia, Links
* Traverson
* Maintainability
* HAL-FORMS
* Metadata

__Why?__ Features

* Discoverability, Hypermedia, Links
* Collections, items, sub-resources
* Projections & excerpts
* Paging, sorting
* Object graph
* Search
* CRUD, associations
* Events
* Custom controllers
* Security
* Metadata
* Traverson

From the Spring Data REST page. 

* Exposes a discoverable REST API for your domain model using HAL as media type.
* Exposes collection, item and association resources representing your model.
* Supports pagination via navigational links.
* Allows to dynamically filter collection resources.
* Exposes dedicated search resources for query methods defined in your repositories.
* Allows to hook into the handling of REST requests by handling Spring ApplicationEvents.
* Exposes metadata about the model discovered as ALPS and JSON Schema.
* Allows to define client specific representations through projections.
* Ships a customized variant of the HAL Browser to leverage the exposed metadata.
* Currently supports JPA, MongoDB, Neo4j, Solr, Cassandra, Gemfire.
* Allows advanced customizations of the default resources exposed.

__Avoid__ Spring Data REST if HATEOAS is not an option.

__Why?__ It can't be switched off

__Avoid__ Spring Data REST if no services are data-centric (CRUD)

__Why?__ Spring Data REST adds no value.

__Do__ add the `Projection` suffix to projection classes. `ItemListProjection`

__Why?__

__Consider__ using the adapter pattern to create projections.

__Why?__

# Access

__Do__ use the `ANOTATION` [detection strategy](https://docs.spring.io/spring-data/rest/docs/current/reference/html/#getting-started.setting-repository-detection-strategy)

__Why?__ It is the most explicit and restrictive of the detection strategies which should help to avoid accidental exposure of repos.

__Do__ only implement the repository methods that are needed.

__Why?__

__Do__ use `@RestResource` to restrict access to methods.

__Why?__

__Do__ use Spring Security on repository methods.

__Why?__

# Example

[User MIcroservice](https://github.com/marklishman/user-microservice)
