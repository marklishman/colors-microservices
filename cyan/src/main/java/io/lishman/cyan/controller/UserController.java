package io.lishman.cyan.controller;

import io.lishman.cyan.model.User;
import io.lishman.cyan.model.UserEvent;
import io.lishman.cyan.service.WebClientUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/users")
class UserController {

    final WebClientUserService webClientUserService;

    UserController(WebClientUserService webClientUserService) {
        this.webClientUserService = webClientUserService;
    }

    @GetMapping
    ResponseEntity<Flux<User>> getUsers() {
        return webClientUserService.getUsers();
    }

    @GetMapping("/{id}")
    ResponseEntity<Mono<User>> getUser(@PathVariable("id") final String id) {
        return webClientUserService.getUser(id);
    }

    @GetMapping("webflux")
    ResponseEntity<String> webFluxClient() {
        webClientUserService.webFluxClient();
        return ResponseEntity.ok("done");
    }

    @PostMapping("/events/toggle")
    @ResponseStatus(HttpStatus.OK)
    ResponseEntity<String> toggleEventStream() {
        final String status = webClientUserService.toggleUserEventStream();
        return ResponseEntity.ok(status);
    }

    @GetMapping("/events/latest")
    ResponseEntity<UserEvent> latestEventStream() {
        final UserEvent userEvent = webClientUserService.getLatestUserEvent();
        return ResponseEntity.ok(userEvent);
    }

}
