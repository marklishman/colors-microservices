package io.lishman.green.controller;

import io.lishman.green.model.InstanceDetails;
import io.lishman.green.service.GreenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreenController.class);

    private final GreenService greenService;

    @Autowired
    public GreenController(GreenService greenService) {
        this.greenService = greenService;
    }

    @GetMapping("/")
    public InstanceDetails getCallDetails() {
        LOGGER.info("Called Green");
        return greenService.getInstanceDetails();
    }
}
