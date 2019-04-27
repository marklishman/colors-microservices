package io.lishman.webflux.handler;

import io.lishman.webflux.model.Employee;
import io.lishman.webflux.model.EmployeeEvent;
import io.lishman.webflux.repository.EmployeeRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.BodyInserters.fromObject;

@Component
public class EmployeeHandler {

    private EmployeeRepository employeeRepository;

    public EmployeeHandler(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

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

    public Mono<ServerResponse> saveEmployee(ServerRequest request) {
        Mono<Employee> employeeMono = request.bodyToMono(Employee.class);

        return employeeMono.flatMap(Employee ->
                ServerResponse.status(HttpStatus.CREATED)
                        .contentType(APPLICATION_JSON)
                        .body(employeeRepository.save(Employee), Employee.class));
    }

    public Mono<ServerResponse> updateEmployee(ServerRequest request) {
        String id = request.pathVariable("id");
        Mono<Employee> existingEmployeeMono = this.employeeRepository.findById(id);
        Mono<Employee> employeeMono = request.bodyToMono(Employee.class);

        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return employeeMono
                .zipWith(existingEmployeeMono,
                        (employee, existingEmployee) ->
                                new Employee(
                                        id,
                                        employee.getName(),
                                        employee.getEmail(),
                                        employee.getPhone(),
                                        employee.getEmployeeNumber()
                                )
                )
                .flatMap(employee ->
                        ServerResponse.ok()
                                .contentType(APPLICATION_JSON)
                                .body(employeeRepository.save(employee), Employee.class)
                ).switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteEmployee(ServerRequest request) {
        String id = request.pathVariable("id");

        Mono<Employee> employeeMono = this.employeeRepository.findById(id);
        Mono<ServerResponse> notFound = ServerResponse.notFound().build();

        return employeeMono
                .flatMap(existingEmployee ->
                        ServerResponse.ok()
                                .build(employeeRepository.delete(existingEmployee))
                )
                .switchIfEmpty(notFound);
    }

    public Mono<ServerResponse> deleteAllEmployees(ServerRequest request) {
        return ServerResponse.ok()
                .build(employeeRepository.deleteAll());
    }

    public Mono<ServerResponse> getEmployeeEvents(ServerRequest request) {
        Flux<EmployeeEvent> eventsFlux = Flux.interval(Duration.ofSeconds(2)).map(val ->
                new EmployeeEvent(val, "Employee Event " + val)
        );

        return ServerResponse.ok()
                .contentType(MediaType.TEXT_EVENT_STREAM)
                .body(eventsFlux, EmployeeEvent.class);
    }
}
