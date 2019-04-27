package io.lishman.cyan.controller;

import io.lishman.cyan.model.User;
import io.lishman.cyan.service.FeignUsersService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
class UserController {

    private final FeignUsersService feignUsersService;

    UserController(final FeignUsersService feignUsersService) {
        this.feignUsersService = feignUsersService;
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
