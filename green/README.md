# Green

## Features

* Spring Boot
* Spring Data JPA
* Spring HATEOAS
* Testing

---

# Getting Started

### Postgres
* `docker start ref-db`
* Create a schema called `green`
* Run the `scripts/sql/schema.sql` script in this schema.
* Run the `scripts/sql/data.sql` script in this schema.

### HTTP
* Run the HTTP scripts in the `/scripts/http` directory


# HAL
     
~~~java
@GetMapping(produces = "application/hal+json")
public ResponseEntity<Resources<UserResource>> getUsersWithHal() {
    final List<User> users = userService.getAllUsers();

    final List<UserResource> userResourceList = UserResourceAssembler
            .getInstance()
            .toResources(users);
    final Resources<UserResource> userResources = new Resources<>(userResourceList);
    userResources.add(linkTo(methodOn(getClass()).getUsersWithHal()).withSelfRel());
    return ResponseEntity.ok(userResources);
}
~~~

NOTE that the collection link is being added manually here. In other words we are not using a `ResourceAssembler`.
There are some significant changes regarding this in the next version of Spring.
