package io.lishman.webflux.contoller;

import io.lishman.webflux.model.User;
import io.lishman.webflux.model.UserEvent;
import io.lishman.webflux.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

@RestController
@RequestMapping("/controller/users")
public class UserController {

    private final UserRepo userRepo;

    @Autowired
    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public Flux<User> getAllUsers() {
        return userRepo.findAll();
    }


    @GetMapping("/{id}")
    public Mono<ResponseEntity<User>> getUser(@PathVariable("id") final String id) {
        return userRepo.findById(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<User> saveUser(@RequestBody User user) {
        return userRepo.save(user);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable("id") String id, @RequestBody User user) {
        return userRepo
                .findById(id)
                .flatMap(existingUser -> {
                    var updatedUser = new User(
                            id,
                            user.getName(),
                            user.getEmail(),
                            user.getPhone(),
                            user.getUsername(),
                            user.getWebsite()
                    );
                    return userRepo.save(updatedUser);
                })
                .map(updateUser -> ResponseEntity.ok(updateUser))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteUser(@PathVariable("id") String id) {
        return userRepo.findById(id)
                .flatMap(existingUser ->
                        userRepo.delete(existingUser)
                            .then(Mono.just(ResponseEntity.ok().<Void>build()))
                )
                .defaultIfEmpty(ResponseEntity.notFound().build());

    }

    @DeleteMapping
    public Mono<Void> deleteAll() {
        return userRepo.deleteAll();
    }

    @GetMapping(value = "/events", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<UserEvent> getUserEvents() {
        return Flux.interval(Duration.ofSeconds(1))
                .map(val ->
                        new UserEvent(val, "User Event")
                );
    }

}
