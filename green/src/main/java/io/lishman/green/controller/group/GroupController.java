package io.lishman.green.controller.group;

import io.lishman.green.model.Group;
import io.lishman.green.service.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/groups")
class GroupController {

    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public ResponseEntity<Resources<GroupResource>> getGroups() {
        final List<Group> groups = groupService.getAllGroups();

        final List<GroupResource> groupResourceList = GroupResourceAssembler
                .getInstance()
                .toResources(groups);

        /**
         * NOTE that the collection link is being added manually here.
         * In other words we are not using a ResourceAssembler.
         *
         * There are some significant changes regarding this in the
         * next version of Spring.
         */
        final Resources<GroupResource> groupResources = new Resources<>(groupResourceList);
        groupResources.add(linkTo(methodOn(getClass()).getGroups()).withSelfRel());
        return ResponseEntity.ok(groupResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResource> getGroup(@PathVariable("id") final Long id) {
        final var group = groupService.getById(id);
        var groupResource = GroupResourceAssembler
                .getInstance()
                .toResource(group);
        return ResponseEntity.ok(groupResource);

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Group createGroup(@RequestBody final Group group) {
        return groupService.createGroup(group);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Group updateDetails(
            @PathVariable("id") final Long id,
            @RequestBody final Group group) {
        return groupService.updateGroup(id, group);
    }
}
