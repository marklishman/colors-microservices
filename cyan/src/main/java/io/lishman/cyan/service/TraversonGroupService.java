package io.lishman.cyan.service;

import io.lishman.cyan.model.Group;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.hateoas.client.Traverson;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public final class TraversonGroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TraversonGroupService.class);

    private final Traverson purpleTraverson;

    public TraversonGroupService(Traverson purpleTraverson) {
        this.purpleTraverson = purpleTraverson;
    }

    public Collection<Group> getGroups() {
        LOGGER.info("Get Groups with Traverson as HAL");

        final ParameterizedTypeReference<Resources<Group>> groupsResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resources<Group> GroupsResource = purpleTraverson
                .follow("groups")
                .toObject(groupsResourceTypeReference);

        return GroupsResource.getContent();
    }

    public Group getGroup(final Long pos) {
        LOGGER.info("Get Group at position {} with Traverson as HAL", pos);

        final ParameterizedTypeReference<Resource<Group>> groupResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final String rel = String.format("$._embedded.groups[%s]._links.self.href", pos);

        final Resource<Group> groupResource = purpleTraverson
                .follow("groups", rel)
                .toObject(groupResourceTypeReference);

        return groupResource.getContent();
    }

}
