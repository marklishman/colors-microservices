package io.lishman.blue.service;

import io.lishman.blue.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
public class BlueService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance.name:0}")
    private int instance;

    @Value("${server.port}")
    private int port;

    // TODO Use WebClient
    @Autowired
    private RestTemplate restTemplate;

    public InstanceDetails getInstanceDetails() {

        InstanceDetails instanceDetails = restTemplate.getForObject(
                "http://localhost:8082",
                InstanceDetails.class
        );

        return new InstanceDetails(
                name,
                instance,
                port,
                Collections.singletonList(instanceDetails)
        );
    }
}
