package io.lishman.blue.service;

import io.lishman.blue.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
@RefreshScope
public class BlueService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${token.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${token.config:default blue config}")
    private String config;

    private final RestTemplate restTemplate;

    @Autowired
    public BlueService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public InstanceDetails getInstanceDetails() {

        InstanceDetails greenInstanceDetails = restTemplate.getForObject(
                "http://green",
                InstanceDetails.class
        );

        InstanceDetails redInstanceDetails = restTemplate.getForObject(
                "http://red",
                InstanceDetails.class
        );

        return new InstanceDetails(
                name,
                instance,
                port,
                config,
                Arrays.asList(greenInstanceDetails, redInstanceDetails));
    }
}
