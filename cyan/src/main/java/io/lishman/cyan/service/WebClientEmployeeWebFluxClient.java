package io.lishman.cyan.service;

import io.lishman.cyan.model.Employee;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Component
public class WebClientEmployeeWebFluxClient {

    private final WebClient whiteWebClient;

    WebClientEmployeeWebFluxClient(WebClient whiteWebClient) {
        this.whiteWebClient = whiteWebClient;
    }

    public void runClient() {
        postNewEmployee()
                .thenMany(getAllEmployees())
                .take(1)
                .flatMap(employee -> updateEmployee(employee.getEmployeeId(), "Updated Employee Name"))
                .flatMap(employee -> deleteEmployee(employee.getEmployeeId()))
                .thenMany(getAllEmployees())
                .subscribe(System.out::println);
    }

    private Flux<Employee> getAllEmployees() {
        return whiteWebClient
                .get()
                .uri("/controller/employees")
                .retrieve()
                .bodyToFlux(Employee.class)
                .doOnNext(o -> System.out.println("**********GET: " + o));
    }

    private Mono<ResponseEntity<Employee>> postNewEmployee() {
        return whiteWebClient
                .post()
                .uri("/controller/employees")
                .body(Mono.just(new Employee("four", "employee_four", "four@email.com", "067856469")), Employee.class)
                .exchange()
                .flatMap(response -> response.toEntity(Employee.class))
                .doOnSuccess(o -> System.out.println("**********POST " + o));
    }

    private Mono<Employee> updateEmployee(String id, String name) {
        return whiteWebClient
                .put()
                .uri("/controller/employees/{id}", id)
                .body(Mono.just(new Employee(name, "employee_four", "four@email.com", "067856469")), Employee.class)
                .retrieve()
                .bodyToMono(Employee.class)
                .doOnSuccess(o -> System.out.println("**********UPDATE " + o));
    }

    private Mono<Void> deleteEmployee(String id) {
        return whiteWebClient
                .delete()
                .uri("/controller/employees/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .doOnSuccess(o -> System.out.println("**********DELETE " + o));
    }
}
