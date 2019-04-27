# White

# Project Features

* Webflux

# Set Up

### Database

There is no need to install a database. This microservice uses an embedded MongoDB
database which is started and populated each time the application is executed.

Why MongoDB? Because it supports reactive repositories, JDBC / JPA does not.

---

# Programming Models

There are two programming models supported by Spring WebFlux.

### Annotated Controllers

Consistent with Spring MVC and based on the same annotations from the spring-web module.

~~~java
@GetMapping
public Flux<Employee> getAllEmployees() {
    return employeeRepository.findAll();
}


@GetMapping("/{id}")
public Mono<ResponseEntity<Employee>> getEmployee(@PathVariable("id") final String id) {
    return employeeRepository.findById(id)
            .map(ResponseEntity::ok)
            .defaultIfEmpty(ResponseEntity.notFound().build());
}
~~~

We can also get a stream of objects decoded from the response.

~~~java
@GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
public Flux<EmployeeEvent> getEmployeeEvents() {
    return Flux.interval(Duration.ofSeconds(2))
            .map(val ->
                    new EmployeeEvent(val, "Employee Event")
            );
}
~~~

Note the `TEXT_EVENT_STREAM_VALUE` media type.

If the browser does not support streams, we can use JavaScript instead.

~~~html
<script type="text/javascript">
    if (!!window.EventSource) {
        var evtSource = new EventSource('employees/events'); //http://localhost:8080/index.html
        var eventList = document.querySelector('ul');

        evtSource.onmessage = function(e) {
          var newElement = document.createElement("li");

          newElement.textContent = "Event: " + e.data;
          eventList.appendChild(newElement);
        }
    } else {
        alert("The browser doesn't support SSE");
    }
</script>
~~~


### Functional Endpoints

Lambda-based, lightweight, and functional programming model. You can think of this 
as a small library or a set of utilities that an application can use to route and 
handle requests.

~~~java
public Mono<ServerResponse> getAllEmployees(ServerRequest request) {
    Flux<Employee> employees = employeeRepository.findAll();

    return ServerResponse.ok()
            .contentType(APPLICATION_JSON)
            .body(employees, Employee.class);
}

public Mono<ServerResponse> getEmployee(ServerRequest request) {
    String id = request.pathVariable("id");

    Mono<Employee> employeeMono = this.employeeRepository.findById(id);
    Mono<ServerResponse> notFound = ServerResponse.notFound().build();

    return employeeMono
            .flatMap(employee ->
                    ServerResponse.ok()
                            .contentType(APPLICATION_JSON)
                            .body(fromObject(employee)))
            .switchIfEmpty(notFound);
}
~~~

We get a stream of objects like this 

~~~java
public Mono<ServerResponse> getEmployeeEvents(ServerRequest request) {
    Flux<EmployeeEvent> eventsFlux = Flux.interval(Duration.ofSeconds(2)).map(val ->
            new EmployeeEvent(val, "Employee Event")
    );

    return ServerResponse.ok()
            .contentType(MediaType.TEXT_EVENT_STREAM)
            .body(eventsFlux, EmployeeEvent.class);
}
~~~

With functional endpoints we also need to define routes. 

~~~java
return route(GET("/handler/employees").and(accept(APPLICATION_JSON)), handler::getAllEmployees)
        .andRoute(POST("/handler/employees").and(contentType(APPLICATION_JSON)), handler::saveEmployee)
        .andRoute(DELETE("/handler/employees").and(accept(APPLICATION_JSON)), handler::deleteAllEmployees)
        .andRoute(GET("/handler/employees/events").and(accept(TEXT_EVENT_STREAM)), handler::getEmployeeEvents)
        .andRoute(GET("/handler/employees/{id}").and(accept(APPLICATION_JSON)), handler::getEmployee)
        .andRoute(PUT("/handler/employees/{id}").and(contentType(APPLICATION_JSON)), handler::updateEmployee)
        .andRoute(DELETE("/handler/employees/{id}").and(accept(APPLICATION_JSON)), handler::deleteEmployee);
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
