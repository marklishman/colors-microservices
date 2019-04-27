package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "people", url = "http://localhost:8021/green/users")
public interface FeignUsersClient {

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    List<User> getUsers();

    @GetMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    User getUser(@PathVariable("id") final Long id);
}
