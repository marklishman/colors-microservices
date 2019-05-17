package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public final class RestTemplateUsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestTemplateUsersService.class);

    private final RestTemplate greenRestTemplate;

    public RestTemplateUsersService(final RestTemplate greenRestTemplate) {
        this.greenRestTemplate = greenRestTemplate;
    }

    public User getUser(final Long id) {
        LOGGER.info("Get Users with RestTemplate");

        return greenRestTemplate
            .getForObject("/green/users/{id}", User.class, id);
    }
}
