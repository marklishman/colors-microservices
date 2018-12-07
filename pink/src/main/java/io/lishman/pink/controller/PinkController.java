package io.lishman.pink.controller;

import io.lishman.pink.model.InstanceDetails;
import io.lishman.pink.service.PinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PinkController {

    private final PinkService pinkService;

    @Autowired
    public PinkController(PinkService pinkService) {
        this.pinkService = pinkService;
    }

    @GetMapping("/")
    public InstanceDetails getCallDetails() {
        return pinkService.getInstanceDetails();
    }
}
