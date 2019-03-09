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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    // CRUD

    @GetMapping("/{id}")
    public GreenDetails getGreenDetails(@PathVariable("id") final Long id) {
        LOGGER.info("Get instance details for Green id {}", id);
        return greenService.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GreenDetails createDetails(@RequestBody final GreenDetails greenDetails) {
        // TODO logging
        return greenService.createDetails(greenDetails);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public GreenDetails updateDetails(
            @PathVariable("id") final Long id,
            @RequestBody final GreenDetails greenDetails) {
        // TODO logging
        return greenService.updateDetails(id, greenDetails);
    }

    // Projection

    @GetMapping(value = "/{id}", params = "projection=instance")
    public InstanceDetails getInstanceDetails(@PathVariable("id") final Long id) {
        LOGGER.info("Get instance details for Green id {}", id);
        return greenService.getInstanceDetails(id);
    }

    // Search

    @GetMapping(value = "/search/findByCorrelationId", params = "correlationId")
    public GreenDetails findByCorrelationId(@RequestParam("correlationId") final String correlationId) {
        LOGGER.info("Get instance details for Green correlation id {}", correlationId);
        return greenService.getDetailsForCorrelationId(correlationId);
    }
}
