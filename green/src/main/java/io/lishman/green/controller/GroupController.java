package io.lishman.green.controller;

import io.lishman.green.model.Group;
import io.lishman.green.service.GroupService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/group")
public class GroupController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GroupController.class);
    private final GroupService groupService;

    @Autowired
    public GroupController(GroupService groupService) {
        this.groupService = groupService;
    }

    // CRUD

    @GetMapping("/{id}")
    public Group getGroup(@PathVariable("id") final Long id) {
        LOGGER.info("Get instance details for Green id {}", id);
        return groupService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Group createGroup(@RequestBody final Group group) {
        // TODO logging
        return groupService.createDetails(group);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Group updateDetails(
            @PathVariable("id") final Long id,
            @RequestBody final Group group) {
        // TODO logging
        return groupService.updateDetails(id, group);
    }

    // TODO commented out code

    // Projection

//    @GetMapping(value = "/{id}", params = "projection=instance")
//    public InstanceDetails getInstanceDetails(@PathVariable("id") final Long id) {
//        LOGGER.info("Get instance details for Green id {}", id);
//        return groupService.getInstanceDetails(id);
//    }

    // Search

//    @GetMapping(value = "/search/findByCorrelationId", params = "correlationId")
//    public GreenDetails findByCorrelationId(@RequestParam("correlationId") final String correlationId) {
//        LOGGER.info("Get instance details for Green correlation id {}", correlationId);
//        return groupService.getDetailsForCorrelationId(correlationId);
//    }
}
