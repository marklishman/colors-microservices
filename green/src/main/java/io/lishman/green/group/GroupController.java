package io.lishman.green.group;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

@RestController
@RequestMapping("/groups")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping()
    public ResponseEntity<GroupResources> getGroups() {
        LOGGER.info("Get all groups");
        final List<Group> groups = groupService.getAllGroups();
        final GroupResources groupResources = GroupResources.fromGroups(groups);
        return ResponseEntity.ok(groupResources);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GroupResource> getGroup(@PathVariable("id") final Long id) {
        LOGGER.info("Get group for id {}", id);
        final var group = groupService.getById(id);
        final var groupResource = GroupResource.fromGroup(group);
        return ResponseEntity.ok(groupResource);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Group createGroup(@RequestBody final Group group) {
        // TODO logging
        return groupService.createGroup(group);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Group updateDetails(
            @PathVariable("id") final Long id,
            @RequestBody final Group group) {
        // TODO logging
        return groupService.updateGroup(id, group);
    }
}
