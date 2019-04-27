package io.lishman.cyan.service;

import io.lishman.cyan.model.Employee;
import io.lishman.cyan.model.EmployeeEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public final class WebClientEmployeeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientEmployeeService.class);

    private final WebClient whiteWebClient;
    private final WebClientEmployeeWebFluxClient webClientEmployeeWebFluxClient;

    private Disposable disposable;
    private EmployeeEvent latestEmployeeEvent;

    public WebClientEmployeeService(WebClient whiteWebClient, final WebClientEmployeeWebFluxClient webClientEmployeeWebFluxClient) {
        this.whiteWebClient = whiteWebClient;
        this.webClientEmployeeWebFluxClient = webClientEmployeeWebFluxClient;
    }

    public ResponseEntity<Flux<Employee>> getEmployees() {
        LOGGER.info("Get Employees with WebClient from WebFlux");
        final Flux<Employee> employees = whiteWebClient
                .get()
                .uri("/controller/employees")
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToFlux(Employee.class);

        return ResponseEntity.ok(employees);
    }

    public ResponseEntity<Mono<Employee>> getEmployee(final String id) {
        LOGGER.info("Get Employee {} with WebClient from WebFlux", id);
        final Mono<Employee> employee = whiteWebClient
                .get()
                .uri("/controller/employees/{id}", id)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Employee.class);

        return ResponseEntity.ok(employee);
    }

    public void webFluxClient() {
        LOGGER.info("Multiple calls to WebFux API with WebClient");
        webClientEmployeeWebFluxClient.runClient();
    }

    public String toggleEmployeeEventStream() {
        return this.disposable == null ?
                startEmployeeEventStream() :
                stopEmployeeEventStream();
    }

    public EmployeeEvent getLatestEmployeeEvent() {
        return latestEmployeeEvent;
    }

    private String startEmployeeEventStream() {
        LOGGER.info("Starting Employee Event Stream");
        disposable = whiteWebClient
                .get()
                .uri("/controller/employees/events")
                .accept(MediaType.TEXT_EVENT_STREAM)
                .retrieve()
                .bodyToFlux(EmployeeEvent.class)
                .log()
                .subscribe(employeeEvent -> latestEmployeeEvent = employeeEvent);
        return "started";
    }

    private String stopEmployeeEventStream() {
        LOGGER.info("Stopping Employee Event Stream");
        disposable.dispose();
        disposable = null;
        return "stopped";
    }

}
