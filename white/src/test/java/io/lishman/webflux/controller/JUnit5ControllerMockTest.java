package io.lishman.webflux.controller;

import io.lishman.webflux.contoller.EmployeeController;
import io.lishman.webflux.model.Employee;
import io.lishman.webflux.model.EmployeeEvent;
import io.lishman.webflux.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
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
class JUnit5ControllerMockTest {

    private static final String USER_ID = UUID.randomUUID().toString();

    private WebTestClient client;

    private List<Employee> expectedList;

    @MockBean
    private EmployeeRepository repository;

    @BeforeEach
    void beforeEach() {
        this.client =
                WebTestClient
                        .bindToController(new EmployeeController(repository))
                        .configureClient()
                        .baseUrl("/controller/users")
                        .build();

        this.expectedList = Arrays.asList(
                new Employee(USER_ID, "one", "user_one", "one@email.com", "01234567", "www.one.com")
        );
    }

    @Test
    void testGetAllUsers() {
        when(repository.findAll()).thenReturn(Flux.fromIterable(this.expectedList));

        client
                .get()
                .uri("/")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBodyList(Employee.class)
                .isEqualTo(expectedList);
    }

    @Test
    void testUserInvalidIdNotFound() {
        String id = "aaa";
        when(repository.findById(id)).thenReturn(Mono.empty());

        client
                .get()
                .uri("/{id}", id)
                .exchange()
                .expectStatus()
                .isNotFound();
    }

    @Test
    void testUserIdFound() {
        Employee expectedEmployee = this.expectedList.get(0);
        when(repository.findById(expectedEmployee.getEmployeeId())).thenReturn(Mono.just(expectedEmployee));

        client
                .get()
                .uri("/{id}", USER_ID)
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(Employee.class)
                .isEqualTo(expectedEmployee);
    }

    @Test
    void testUserEvents() {
        EmployeeEvent expectedEvent =
                new EmployeeEvent(0L, "Employee Event");

        FluxExchangeResult<EmployeeEvent> result =
                client.get().uri("/events")
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
