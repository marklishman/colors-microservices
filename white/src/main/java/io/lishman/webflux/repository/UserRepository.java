package io.lishman.webflux.repository;

import io.lishman.webflux.model.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface UserRepository extends ReactiveMongoRepository<User, String> {

    Flux<User> findByUsername(String username);

}
