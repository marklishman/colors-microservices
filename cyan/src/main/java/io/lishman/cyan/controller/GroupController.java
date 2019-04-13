package io.lishman.cyan.controller;

import io.lishman.cyan.model.Group;
import io.lishman.cyan.service.GroupService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/groups")
class GroupController {

    final GroupService groupService;

    GroupController(final GroupService groupService) {
        this.groupService = groupService;
    }

    @GetMapping
    ResponseEntity<Collection<Group>> getGroups() {
        final Collection<Group> groups = groupService.getGroups();
        return ResponseEntity.ok(groups);
    }

    @GetMapping("{id}")
    ResponseEntity<Group> getGroupById(@PathVariable("id") final Long id) {
        final Group group = groupService.getGroup(id);
        return ResponseEntity.ok(group);
    }

}
