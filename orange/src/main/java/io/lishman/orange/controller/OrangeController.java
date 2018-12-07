package io.lishman.orange.controller;

import io.lishman.orange.model.InstanceDetails;
import io.lishman.orange.service.OrangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrangeController {

    private final OrangeService orangeService;

    @Autowired
    public OrangeController(OrangeService orangeService) {
        this.orangeService = orangeService;
    }

    @GetMapping("/")
    public InstanceDetails getCallDetails() {
        return orangeService.getInstanceDetails();
    }
}
