# Green

## Features

* REST API
* Spring Boot
* Spring Data JPA
* Spring HATEOAS
* Testing

---

# Getting Started

* Start the `ref-db` database
* Start this service (`green`)
* Run the HTTP scripts in the `/scripts/http` directory

---

# To Do

* CRUD

* Spring HATEOAS
* HAL
* Resource
* ResourceAssembler

* Exception Handling
* Custom Exceptions
* @ControllerAdvice
* `server.error.include-stacktrace`

* Entity
* Model
* Resource

* `show_sql`

---

# CRUD

`UserContoller` includes typical CRUD methods.

~~~java
@PostMapping
@ResponseStatus(HttpStatus.CREATED)
public User createUser(@RequestBody final User user) {
    return userService.createUser(user);
}

@PutMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void updateUser(
        @PathVariable("id") final Long id,
        @RequestBody final User user) {
    userService.updateUser(id, user);
}

@DeleteMapping("/{id}")
@ResponseStatus(HttpStatus.NO_CONTENT)
public void deleteUser(@PathVariable("id") final Long id) {
    userService.deleteUser(id);
}
~~~

# Representations

`UserController` also allows resources to be returned as plain JSON

~~~java
@GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<List<User>> getUsers() {
    final List<User> users = userService.getAllUsers();
    return ResponseEntity.ok(users);
}
~~~

~~~java
@GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
public ResponseEntity<User> getUser(@PathVariable("id") final Long id) {
    final var user = userService.getUserById(id);
    return ResponseEntity.ok(user);

}
~~~

or as HAL format if the `Accept` request header is set to application/hal+json

    GET http://localhost:8021/green/users
    Accept: application/hal+json

~~~java
@GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
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

and

~~~java
@GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
public ResponseEntity<UserResource> getUserWithHal(@PathVariable("id") final Long id) {
    final var user = userService.getUserById(id);
    var userResource = UserResourceAssembler
            .getInstance()
            .toResource(user);
    return ResponseEntity.ok(userResource);
}
~~~




























======================================================================================

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



# Resource

From [Stack Overflow](https://stackoverflow.com/questions/21346387/how-to-correctly-use-pagedresourcesassembler-from-spring-data/21362291#21362291)

* `Resource` - an item resource. Effectively to wrap around some DTO or entity that captures a single item and enriches it with links.
* `Resources` - a collection resource, that can be a collection of somethings but usually are a collection of Resource instances.
* `PagedResources` - an extension of Resources that captures additional pagination information like the number of total pages etc.

All of these classes derive from `ResourceSupport`, which is a basic container for `Link` instances.

---

# Exception Handling

Never show stack trace

~~~yml
server:
  error:
    include-stacktrace: never
~~~

We throw this exception when a resource is not found.

~~~java
public class UserResourceNotFoundException extends ResourceNotFoundException {
    public UserResourceNotFoundException(final Long id) {
        super(String.format("User %s not found", id));
    }
}
~~~

which returns this JSON in the response body.

~~~json
{
  "timestamp": "2019-05-04T10:13:19.415+0000",
  "status": 404,
  "error": "Not Found",
  "message": "User 6888 not found",
  "path": "/green/users/6888"
}
~~~

However, when we do a mock MVC test this body is not returned.

~~~java
mvc.perform(get("/users/{id}", USER_ID).accept(MediaType.APPLICATION_JSON))
        .andExpect(status().is(404));
~~~

~~~
MockHttpServletResponse:
           Status = 404
    Error message = null
          Headers = []
     Content type = null
             Body = 
    Forwarded URL = null
   Redirected URL = null
          Cookies = []
~~~ 

The reason for this is explained here - https://github.com/spring-projects/spring-boot/issues/7321

> Spring Boot's error handling is based on Servlet container error mappings that result in an ERROR dispatch to an ErrorController. 
MockMvc however is container-less testing so with no Servlet container the exception simply bubbles up with nothing to stop it.
  
> So MockMvc tests simply aren't enough to test error responses generated through Spring Boot. 
I would argue that you shouldn't be testing Spring Boot's error handling. If you're customizing it in any way you can write Spring Boot integration tests (with an actual container) to verify error responses. And then for MockMvc tests focus on fully testing the web layer while expecting exceptions to bubble up.
  
> This is a typical unit vs integration tests trade off. You do unit tests even if they don't test everything because 
they give you more control and run faster.

So instead we must use a real web server to perform the test.

~~~java
@SpringBootTest( webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
~~~

~~~java
@Test
@DisplayName("Given user does not exist, " +
        "when get request on the /users/{id} endpoint, " +
        "then 404 status is returned")
void getUserByIdNotFound() throws Exception {

    given(userService.getUserById(USER_ID)).willThrow(new UserResourceNotFoundException(USER_ID));

    this.webTestClient
            .get()
            .uri("/green/users/{id}", USER_ID)
            .accept(MediaType.APPLICATION_JSON)
            .exchange()
            .expectStatus().isNotFound()
            .expectBody().jsonPath("$.message").isEqualTo(String.format("User %s not found", USER_ID));
    }
~~~
