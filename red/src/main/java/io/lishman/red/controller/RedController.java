package io.lishman.red.controller;

import io.lishman.red.model.InstanceDetails;
import io.lishman.red.service.RedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedController {

    private final RedService redService;

    @Autowired
    public RedController(RedService redService) {
        this.redService = redService;
    }

    @GetMapping
    public InstanceDetails getCallDetails() {
        return redService.getInstanceDetails();
    }
}
