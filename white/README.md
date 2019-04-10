# White

### README

* [ ] `/events`
* [ ] JavaScript in `index.html`
* [ ] tests

### Style Guide

* [ ] Favour Annotation based model, more familiar
* [ ] Don't use unless absolutely necessary
* [ ] Don't use of any part of app is synchronous (eg database driver)

---

# Features

* Webflux

# Set Up

### Database

No need to install a database. This microservice uses an embedded Mongodb
database which is started and populated when the application starts.

# Programming Models

There are two programming models supported by Spring WebFlux.

### Annotated Controllers

Consistent with Spring MVC and based on the same annotations from the spring-web module.

~~~java
@GetMapping
public Flux<User> getAllUsers() {
    return userRepository.findAll();
}


@GetMapping("/{id}")
public Mono<ResponseEntity<User>> getUser(@PathVariable("id") final String id) {
    return userRepository.findById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
}
~~~


### Functional Endpoints

Lambda-based, lightweight, and functional programming model. You can think of this 
as a small library or a set of utilities that an application can use to route and 
handle requests.

~~~java
public Mono<ServerResponse> getAllUsers(ServerRequest request) {
    Flux<User> users = userRepository.findAll();

    return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(users, User.class);
}

public Mono<ServerResponse> getUser(ServerRequest request) {
    String id = request.pathVariable("id");

    Mono<User> userMono = this.userRepository.findById(id);
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    return userMono
            .flatMap(user ->
                    ServerResponse.ok()
                            .contentType(APPLICATION_JSON)
                            .body(fromObject(user)))
            .switchIfEmpty(notFound);
}
~~~

---

## Mutable State

In Reactor and RxJava, you declare logic through operators, and, at runtime, a reactive 
pipeline is formed where data is processed sequentially, in distinct stages. A key benefit 
of this is that it frees applications from having to protect mutable state because 
application code within that pipeline is never invoked concurrently.

---

# Reference

Spring Webflux: Getting Started by Esteban Herrera on PluralSight
