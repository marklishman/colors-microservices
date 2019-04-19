package io.lishman.cyan.controller;

import io.lishman.cyan.model.User;
import io.lishman.cyan.model.UserEvent;
import io.lishman.cyan.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/users")
class UserController {

    final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    ResponseEntity<Flux<User>> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/latest")
    ResponseEntity<UserEvent> getLatestUser() {
        final UserEvent userEvent = userService.getLatestUserEvent();
        return ResponseEntity.ok(userEvent);
    }

}
