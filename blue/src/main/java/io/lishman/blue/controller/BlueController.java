package io.lishman.blue.controller;

import io.lishman.blue.model.InstanceDetails;
import io.lishman.blue.service.BlueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlueController {

    @Autowired
    private BlueService blueService;

    @GetMapping
    public InstanceDetails getCallDetails() {
        return blueService.getInstanceDetails();
    }
}