package io.lishman.green.controller;

import io.lishman.green.model.GreenDetails;
import io.lishman.green.model.InstanceDetails;
import io.lishman.green.service.GreenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/green")
public class GreenController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GreenController.class);

    private final GreenService greenService;

    @Autowired
    public GreenController(GreenService greenService) {
        this.greenService = greenService;
    }

    @GetMapping("/{id}")
    public InstanceDetails getInstanceDetails(@PathVariable final Long id) {
        LOGGER.info("Get instance details for Green id {}", id);
        return greenService.getInstanceDetails(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GreenDetails createDetails(@PathVariable("instnce") final String instance,
                                      @RequestBody final GreenDetails greenDetails) {
        return greenService.saveDetails(greenDetails);
    }
}
