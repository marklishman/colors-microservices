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

/**
 *  Traverson
 */

@Service
public final class GroupService {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupService.class);

    private final Traverson greenTraverson;

    public GroupService(Traverson greenTraverson) {
        this.greenTraverson = greenTraverson;
    }

    public Collection<Group> getGroups() {
        LOGGER.info("Get Groups with HAL");

        final ParameterizedTypeReference<Resources<Group>> groupsResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final Resources<Group> GroupsResource = greenTraverson
                .follow("groups")
                .toObject(groupsResourceTypeReference);

        return GroupsResource.getContent();
    }

    public Group getGroup(final Long pos) {
        LOGGER.info("Get Group {} with HAL", pos);

        final ParameterizedTypeReference<Resource<Group>> groupResourceTypeReference =
                new ParameterizedTypeReference<>() {};

        final String rel = String.format("$._embedded.groups[%s]._links.self.href", pos);

        final Resource<Group> groupResource = greenTraverson
                .follow(rel)
                .toObject(groupResourceTypeReference);

        return groupResource.getContent();
    }

}
