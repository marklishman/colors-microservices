package io.lishman.cyan.controller;

import io.lishman.cyan.model.User;
import io.lishman.cyan.service.FeignUsersService;
import io.lishman.cyan.service.WebClientUsersService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
class UsersController {

    final WebClientUsersService webClientUsersService;
    private final FeignUsersService feignUsersService;

    UsersController(final WebClientUsersService webClientUsersService, final FeignUsersService feignUsersService) {
        this.webClientUsersService = webClientUsersService;
        this.feignUsersService = feignUsersService;
    }

    // ~~~~ JSON

    @GetMapping
    ResponseEntity<List<User>> getUsers() {
        final List<User> users = webClientUsersService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "{id}")
    ResponseEntity<User> getUserById(@PathVariable("id") final Long id) {
        final User user = webClientUsersService.getUser(id);
        return ResponseEntity.ok(user);
    }

    // ~~~~ HAL

    @GetMapping(params = "format=hal")
    ResponseEntity<List<User>> getUsersWithHal() {
        final List<User> users = webClientUsersService.getUsersAsHal();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "{id}", params = "format=hal")
    ResponseEntity<User> getUserByIdWithHal(@PathVariable("id") final Long id) {
        final User user = webClientUsersService.getUserAsHal(id);
        return ResponseEntity.ok(user);
    }

    // ~~~~ POST

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody final User user) {
        return webClientUsersService.createUser(user);
    }

    // ~~~~ PUT

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public User updateDetails(
            @PathVariable("id") final Long id,
            @RequestBody final User user) {
        return webClientUsersService.updateUser(id, user);
    }

    // ~~~~ DELETE

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateDetails(@PathVariable("id") final Long id) {
        webClientUsersService.deleteUser(id);
    }

    // ~~~~ Feign

    @GetMapping(params = "client=feign")
    ResponseEntity<List<User>> getUsersWithFeign() {
        final List<User> users = feignUsersService.getUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(value = "{id}", params = "client=feign")
    ResponseEntity<User> getUserByIdWithFeign(@PathVariable("id") final Long id) {
        final User user = feignUsersService.getUser(id);
        return ResponseEntity.ok(user);
    }

}
