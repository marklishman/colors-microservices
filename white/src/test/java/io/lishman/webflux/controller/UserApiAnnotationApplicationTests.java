package io.lishman.webflux.controller;

import io.lishman.webflux.contoller.UserController;
import io.lishman.webflux.model.User;
import io.lishman.webflux.model.UserEvent;
import io.lishman.webflux.repository.UserRepository;
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
public class UserApiAnnotationApplicationTests{

	private WebTestClient client;

	private List<User> expectedList;

	@Autowired
	private UserRepository repository;

	@Before
	public void beforeEach() {
		this.client =
				WebTestClient
						.bindToController(new UserController(repository))
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
				.expectBodyList(User.class)
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
		User expectedUser = expectedList.get(0);
		client
				.get()
				.uri("/{id}", expectedUser.getUserId())
				.exchange()
				.expectStatus()
				.isOk()
				.expectBody(User.class)
				.isEqualTo(expectedUser);
	}

	@Test
	public void testUserEvents() {
		UserEvent expectedEvent =
				new UserEvent(0L, "User Event");

		FluxExchangeResult<UserEvent> result =
				client.get().uri("/events")
						.accept(MediaType.TEXT_EVENT_STREAM)
						.exchange()
						.expectStatus().isOk()
						.returnResult(UserEvent.class);

		StepVerifier.create(result.getResponseBody())
				.expectNext(expectedEvent)
				.expectNextCount(2)
				.consumeNextWith(event ->
						assertThat(Long.valueOf(3), is(equalTo(event.getEventId()))))
				.thenCancel()
				.verify();
	}

}
