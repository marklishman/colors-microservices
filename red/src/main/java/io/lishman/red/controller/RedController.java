package io.lishman.red.controller;

import io.lishman.red.model.InstanceDetails;
import io.lishman.red.service.RedService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RedController {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedController.class);

    private final RedService redService;

    @Autowired
    public RedController(RedService redService) {
        this.redService = redService;
    }

    @GetMapping("/")
    public InstanceDetails getCallDetails() {
        LOGGER.info("Called Red");
        return redService.getInstanceDetails();
    }
}
