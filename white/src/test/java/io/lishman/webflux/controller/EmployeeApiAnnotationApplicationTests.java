package io.lishman.webflux.controller;

import io.lishman.webflux.contoller.EmployeeController;
import io.lishman.webflux.model.Employee;
import io.lishman.webflux.model.EmployeeEvent;
import io.lishman.webflux.repository.EmployeeRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.test.StepVerifier;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EmployeeApiAnnotationApplicationTests {

	private WebTestClient client;

	private List<Employee> expectedList;

	@Autowired
	private EmployeeRepository repository;

	@Before
	public void beforeEach() {
		this.client =
				WebTestClient
						.bindToController(new EmployeeController(repository))
						.configureClient()
						.baseUrl("/controller/users")
						.build();

		this.expectedList =
				repository.findAll().collectList().block();
	}

	@Test
	public void testGetAllUsers() {
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
	public void testUserInvalidIdNotFound() {
		client
				.get()
				.uri("/aaa")
				.exchange()
				.expectStatus()
				.isNotFound();
	}

	@Test
	public void testUserIdFound() {
		Employee expectedEmployee = expectedList.get(0);
		client
				.get()
				.uri("/{id}", expectedEmployee.getEmployeeId())
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(Employee.class)
				.isEqualTo(expectedEmployee);
	}

	@Test
	public void testUserEvents() {
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
