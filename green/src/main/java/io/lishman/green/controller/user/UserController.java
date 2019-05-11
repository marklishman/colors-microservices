package io.lishman.green.controller.user;

import io.lishman.green.model.User;
import io.lishman.green.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/users")
class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<User>> getUsers() {
        final List<User> users = userService.getAllUsers();
        return ResponseEntity.ok(users);
    }

    @GetMapping(produces = "application/hal+json")
    public ResponseEntity<Resources<UserResource>> getUsersWithHal() {
        final List<User> users = userService.getAllUsers();

        final List<UserResource> userResourceList = UserResourceAssembler
                .getInstance()
                .toResources(users);

        final Resources<UserResource> userResources = new Resources<>(userResourceList);
        userResources.add(linkTo(methodOn(getClass()).getUsersWithHal()).withSelfRel());
        return ResponseEntity.ok(userResources);
    }

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable("id") final Long id) {
        final var user = userService.getUserById(id);
        return ResponseEntity.ok(user);

    }

    @GetMapping(value = "/{id}", produces = "application/hal+json")
    public ResponseEntity<UserResource> getUserWithHal(@PathVariable("id") final Long id) {
        final var user = userService.getUserById(id);
        var userResource = UserResourceAssembler
                .getInstance()
                .toResource(user);
        return ResponseEntity.ok(userResource);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User createUser(@RequestBody final User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUser(
            @PathVariable("id") final Long id,
            @RequestBody final User user) {
        userService.updateUser(id, user);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUser(@PathVariable("id") final Long id) {
        userService.deleteUser(id);
    }
}
