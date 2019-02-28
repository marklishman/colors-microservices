package io.lishman.blue.controller;

import io.lishman.blue.model.InstanceDetails;
import io.lishman.blue.service.BlueService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class BlueController {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlueController.class);

    private final BlueService blueService;

    private final DiscoveryClient discoveryClient;

    @Autowired
    public BlueController(BlueService blueService, DiscoveryClient discoveryClient) {
        this.blueService = blueService;
        this.discoveryClient = discoveryClient;
    }

    @GetMapping("/")
    public InstanceDetails getCallDetails() {
        LOGGER.info("Called Blue");
        return blueService.getInstanceDetails();
    }

    @GetMapping("/red-urls")
    public List<String> getRedInstanceUrls() {
        List<ServiceInstance> list = discoveryClient.getInstances("red");
        if (list != null && list.size() > 0 ) {
            return list
                    .stream()
                    .map(ServiceInstance::getUri)
                    .map(URI::toString)
                    .collect(Collectors.toList());
        }
        return null;
    }
}
