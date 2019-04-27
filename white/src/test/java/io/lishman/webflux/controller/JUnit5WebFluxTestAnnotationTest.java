package io.lishman.webflux.controller;

import io.lishman.webflux.contoller.EmployeeController;
import io.lishman.webflux.model.Employee;
import io.lishman.webflux.model.EmployeeEvent;
import io.lishman.webflux.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(EmployeeController.class)
@ActiveProfiles("ControllerTest")
public class JUnit5WebFluxTestAnnotationTest {

    private static final String EMPLOYEE_ID = UUID.randomUUID().toString();

    @Autowired
    private WebTestClient client;

    private List<Employee> expectedList;

    @MockBean
    private EmployeeRepository repository;

    @MockBean
    private CommandLineRunner commandLineRunner;

    @BeforeEach
    void beforeEach() {
        this.expectedList = Arrays.asList(
                new Employee(EMPLOYEE_ID,"one", "employee_one", "one@email.com", "01234567")
        );
    }

    @Test
    void testGetAllEmployees() {
        when(repository.findAll()).thenReturn(Flux.fromIterable(this.expectedList));

        client
                .get()
                .uri("/controller/employees")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Employee.class)
                .isEqualTo(expectedList);
    }

    @Test
    void testEmployeeInvalidIdNotFound() {
        String id = "aaa";
        when(repository.findById(id)).thenReturn(Mono.empty());

        client
                .get()
                .uri("/controller/employees/{id}", id)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testEmployeeIdFound() {
        Employee expectedEmployee = this.expectedList.get(0);
        when(repository.findById(expectedEmployee.getEmployeeId())).thenReturn(Mono.just(expectedEmployee));

        client
                .get()
                .uri("/controller/employees/{id}", expectedEmployee.getEmployeeId())
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Employee.class)
                .isEqualTo(expectedEmployee);
    }

    @Test
    void testEmployeeEvents() {
        EmployeeEvent expectedEvent =
                new EmployeeEvent(0L, "Employee Event 0");

        FluxExchangeResult<EmployeeEvent> result =
                client.get().uri("/controller/employees/events")
                        .accept(MediaType.TEXT_EVENT_STREAM)
                        .exchange()
                        .expectStatus().isOk()
                        .returnResult(EmployeeEvent.class);

        StepVerifier.create(result.getResponseBody())
                .expectNext(expectedEvent)
                .expectNextCount(2)
                .consumeNextWith(event ->
                        assertThat(Long.valueOf(3), is(equalTo(event.getEventId()))))
                .thenCancel()
                .verify();
    }
}
