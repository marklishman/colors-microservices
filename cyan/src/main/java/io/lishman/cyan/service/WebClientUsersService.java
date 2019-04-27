package io.lishman.cyan.service;

import io.lishman.cyan.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;

@Service
public final class WebClientUsersService {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebClientUsersService.class);

    private static final ParameterizedTypeReference<Resource<User>> USER_TYPE_REF = new ParameterizedTypeReference<>() {};
    private static final ParameterizedTypeReference<Resources<Resource<User>>> USERS_TYPE_REF = new ParameterizedTypeReference<>() {};

    private final WebClient greenWebClient;
    private final WebClient greenHalWebClient;

    public WebClientUsersService(final WebClient greenWebClient,
                                 final WebClient greenHalWebClient) {
        this.greenWebClient = greenWebClient;
        this.greenHalWebClient = greenHalWebClient;
    }

    public List<User> getUsers() {
        LOGGER.info("Get Users with WebClient");
        return greenWebClient
                .get()
                .uri("green/users")
                .retrieve()
                .bodyToFlux(User.class)
                .collectList()
                .block();
    }

    public List<User> getUsersAsHal() {
        LOGGER.info("Get Users with WebClient as HAL");
        return greenHalWebClient
                .get()
                .uri("green/users")
                .retrieve()
                .bodyToMono(USERS_TYPE_REF)
                .map(ClientUtils::getResourcesContent)
                .block();
    }

    public User getUser(final Long id) {
        LOGGER.info("Get User {} with WebClient", id);
        return greenWebClient
                .get()
                .uri("green/users/{id}", id)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public User getUserAsHal(final Long id) {
        LOGGER.info("Get User {} with WebClient as HAL", id);
        return greenHalWebClient
                .get()
                .uri("green/users/{id}", id)
                .retrieve()
                .bodyToMono(USER_TYPE_REF)
                .map(Resource::getContent)
                .block();
    }

    public User createUser(final User user) {
        LOGGER.info("Create User with WebClient");
        return greenWebClient
                .post()
                .uri("green/users")
                .syncBody(user)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public User updateUser(final Long id, final User user) {
        LOGGER.info("Update User {} with WebClient", id);
        final User updatedUser = user.cloneWithNewId(id);
        return greenWebClient
                .put()
                .uri("green/users/{id}", id)
                .syncBody(updatedUser)
                .retrieve()
                .bodyToMono(User.class)
                .block();
    }

    public void deleteUser(final Long id) {
        LOGGER.info("Delete User {} with WebClient", id);
        greenWebClient
                .delete()
                .uri("green/users/{id}", id)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}
