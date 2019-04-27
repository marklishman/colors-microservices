package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public final class FeignUsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(FeignUsersService.class);

    private final FeignUsersClient feignUsersClient;

    public FeignUsersService(final FeignUsersClient feignUsersClient) {
        this.feignUsersClient = feignUsersClient;
    }

    public List<User> getUsers() {
        LOGGER.info("Get Users with Feign");
        return feignUsersClient.getUsers();
    }

    public User getUser(final Long id) {
        LOGGER.info("Get User {} with Feign", id);
        return feignUsersClient.getUser(id);
    }
}
