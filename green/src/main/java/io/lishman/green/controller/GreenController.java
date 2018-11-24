package io.lishman.green.controller;

import io.lishman.green.model.InstanceDetails;
import io.lishman.green.service.GreenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreenController {

    @Autowired
    private GreenService greenService;

    @GetMapping
    public InstanceDetails getCallDetails() {
        return greenService.getInstanceDetails();
    }
}
