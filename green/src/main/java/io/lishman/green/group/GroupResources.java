package io.lishman.green.group;

import org.springframework.hateoas.Resources;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

class GroupResources extends Resources<GroupResource> {

    private GroupResources(final List<GroupResource> groupResources) {
        super(groupResources);
    }

    static GroupResources fromGroups(final List<Group> groups) {

        final List<GroupResource> groupResourceList = groups
                .stream()
                .map(GroupResource::fromGroup)
                .collect(Collectors.toList());

        var resources = new GroupResources(groupResourceList);
        resources.add(linkTo(methodOn(GroupController.class).getGroups()).withSelfRel());
        return resources;
    }
}
