package io.lishman.green.group;

import org.springframework.hateoas.Resource;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

class GroupResourceCopy extends Resource<Group> {

    private GroupResourceCopy(final Group group) {
        super(group);
    }

    static GroupResourceCopy fromGroup(final Group group) {
        var resource = new GroupResourceCopy(group);
        resource.add(linkTo(methodOn(GroupController.class).getGroup(null)).withSelfRel());
        return resource;
    }
}
