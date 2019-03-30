package io.lishman.green.group;

import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

class GroupResource extends Resource<Group> {

    private GroupResource(final Group group) {
        super(group);
    }

    static GroupResource fromGroup(final Group group) {
        var resource = new GroupResource(group);
        resource.add(linkTo(methodOn(GroupController.class).getGroup(null)).withSelfRel());
        return resource;
    }
}
