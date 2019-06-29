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

* @ControllerAdvice

---

# CRUD

`UserContoller` includes typical CRUD methods.

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

# HAL

We can also return resources in the HAL format using Spring HATEOAS.

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-hateoas</artifactId>
    </dependency>

For this we create a separate resource class which extends `ResourceSupport`.

~~~java
@Relation(value = "user", collectionRelation = "users")
final public class UserResource extends ResourceSupport {

    private final Long id;
    private final String firstName;
    private final String lastName;
    private final String userName;
    private final String email;
    private final String phoneNumber;
    private final Integer age;
    private final String website;

    private UserResource(final Long id,
                         final String firstName,
                         final String lastName,
                         final String userName,
                         final String email,
                         final String phoneNumber,
                         final Integer age,
                         final String website) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.userName = userName;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.website = website;
    }

    @JsonCreator
    public static UserResource jsonCreator(@JsonProperty("firstName") final String firstName,
                                           @JsonProperty("lastName") final String lastName,
                                           @JsonProperty("userName") final String userName,
                                           @JsonProperty("email") final String email,
                                           @JsonProperty("phoneNumber") final String phoneNumber,
                                           @JsonProperty("age") final Integer age,
                                           @JsonProperty("website") final String website) {
        return new UserResource(
                null,
                firstName,
                lastName,
                userName,
                email,
                phoneNumber,
                age,
                website
        );
    }

    public static UserResource fromUser(final User user) {
        return new UserResource(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getAge(),
                user.getWebsite()
        );
    }

    @JsonProperty("id")
    public Long getUserId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }
    
    ...
    
}
~~~

and use a resource assembler to convert a `User` to a `UserResource`.   

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

~~~java
final class UserResourceAssembler extends ResourceAssemblerSupport<User, UserResource> {

    private UserResourceAssembler() {
        super(UserController.class, UserResource.class);
    }

    public static UserResourceAssembler getInstance() {
        return new UserResourceAssembler();
    }

    @Override
    protected UserResource instantiateResource(final User user) {
        return UserResource.fromUser(user);
    }

    @Override
    public UserResource toResource(final User user) {
        return createResourceWithId(user.getId(), user);
    }
}
~~~

So when we issue a GET request with the `Accept` request header set to `application/hal+json`.

    GET http://localhost:8021/green/users
    Accept: application/hal+json

the response body will look like this.

~~~json
{
  "firstName": "Chelsey",
  "lastName": "Dietrich",
  "userName": "Kamren",
  "email": "Lucio_Hettinger@annie.ca",
  "phoneNumber": "(254)954-1289",
  "age": 36,
  "website": "demarco.info",
  "_links": {
    "self": {
      "href": "http://localhost:8021/green/users/5"
    }
  },
  "id": 5
}
~~~

We use the `UserResource` to return a collection also.

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

NOTE that the _collection_ link is being added manually here. In other words we are not using a `ResourceAssembler`.
There are some significant changes regarding this in the next version of Spring which will improve this.

The response body looks like this.

~~~json
{
  "_embedded": {
    "users": [
      {
        "firstName": "Leanne",
        "lastName": "Graham",
        "userName": "Bret",
        "email": "Sincere@april.biz",
        "phoneNumber": "1-770-736-8031 x56442",
        "age": 17,
        "website": "hildegard.org",
        "_links": {
          "self": {
            "href": "http://localhost:8021/green/users/1"
          }
        },
        "id": 1
      },
      {
        "firstName": "Ervin",
        "lastName": "Howell",
        "userName": "Antonette",
        "email": "Shanna@melissa.tv",
        "phoneNumber": "010-692-6593 x09125",
        "age": 25,
        "website": "anastasia.net",
        "_links": {
          "self": {
            "href": "http://localhost:8021/green/users/2"
          }
        },
        "id": 2
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8021/green/users"
    }
  }
}
~~~

# Resource

From [Stack Overflow](https://stackoverflow.com/questions/21346387/how-to-correctly-use-pagedresourcesassembler-from-spring-data/21362291#21362291)

* `Resource` - an item resource. Effectively to wrap around some DTO or entity that captures a single item and enriches it with links.
* `Resources` - a collection resource, that can be a collection of somethings but usually are a collection of Resource instances.
* `PagedResources` - an extension of Resources that captures additional pagination information like the number of total pages etc.

All of these classes derive from `ResourceSupport`, which is a basic container for `Link` instances.

---

# Exception Handling

We use this base class for missing resources

~~~java
@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(final String message) {
        super(message);
    }
}
~~~

and extend it with specific missing resource exceptions.

~~~java
public class UserResourceNotFoundException extends ResourceNotFoundException {

    public UserResourceNotFoundException(final Long id) {
        super(String.format("User %s not found", id));
    }
}
~~~

This makes it very readable in the code.

~~~java
if (!userRepository.existsById(id)) {
    throw new UserResourceNotFoundException(id);
}
~~~

In production we make sure that the response body does not include a stack trace by including this in `application.yml`

~~~yml
server:
  error:
    include-stacktrace: never
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

However, in development we do want to include the stack trace in the JSON so we do so by adding this to `application-dev.yml`. 

~~~yml
spring:
  jpa:
    properties:
      hibernate:
        show_sql: true
        use_sql_comments: true
        format_sql: true
~~~

# Handling Exceptions

To intercept exceptions and customize the output we use a `@ControllerAdvice` with `@ExceptionHandler`.

~~~java
@ControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = SomeSpecialException.class)
    protected ResponseEntity<CustomExceptionBody> handleSpecialCase(final SomeSpecialException ex, final WebRequest request) {

        final CustomExceptionBody body = new CustomExceptionBody(ex.getCode(), ex.getDescription());

        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
~~~

---

# Testing















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

---

# Domain Objects

In this application we have chosen to separate the user into three separate classes.

* `User` - the business object.
* `UserEntity` - an entity object used for database persistence.
* `UserResource` - represents the resource used in the API.

---

# SQL Output

When running in dev mode (ie with the `dev` profile) we have chosen to output formatted SQL of all the database queries.

    spring:
      jpa:
        properties:
          hibernate:
            show_sql: true
            use_sql_comments: true
            format_sql: true
