package io.lishman.cyan.service;

import io.lishman.cyan.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;

import static org.springframework.hateoas.client.Hop.rel;

/**
 *  Traverson
 */

@Service
public final class GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    public Collection<Group> getGroups() {
        LOGGER.info("Get Groups with HAL");

        final ParameterizedTypeReference<Resources<Group>> GroupsResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Traverson traverson = new Traverson(getUri("http://localhost:8061/purple/api/groups"), MediaTypes.HAL_JSON);

        Resources<Group> GroupsResource = traverson
                .follow(rel("self"))
                .toObject(GroupsResourceTypeReference);

        return GroupsResource.getContent();
    }

    public Group getGroup(final Long pos) {
        LOGGER.info("Get Group {} with HAL", pos);

        final ParameterizedTypeReference<Resource<Group>> groupResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Traverson traverson = new Traverson(getUri("http://localhost:8061/purple/api/groups"), MediaTypes.HAL_JSON);

        final String rel = String.format("$._embedded.groups[%s]._links.self.href", pos);

        Resource<Group> groupResource = traverson
                .follow(rel)
                .toObject(groupResourceTypeReference);

        return groupResource.getContent();
    }

    private URI getUri(final String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
