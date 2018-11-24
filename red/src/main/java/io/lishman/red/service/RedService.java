package io.lishman.red.service;

import io.lishman.red.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;

@Service
public class RedService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:1}")
    private int instance;

    @Value("${server.port}")
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    public InstanceDetails getInstanceDetails() {

        InstanceDetails greenInstanceDetails = restTemplate.getForObject(
                "http://green:8021",
                InstanceDetails.class
        );

        InstanceDetails orangeInstanceDetails = restTemplate.getForObject(
                "http://orange:8041",
                InstanceDetails.class
        );

        return new InstanceDetails(
                name,
                instance,
                port,
                Arrays.asList(greenInstanceDetails, orangeInstanceDetails)
        );
    }
}
