package io.lishman.blue.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.lishman.blue.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.Collections;

@Service
@RefreshScope
public class BlueService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default blue config}")
    private String config;

    private final GreenClient greenClient;
    private final RestTemplate restTemplate;

    @Autowired
    public BlueService(GreenClient greenClient, RestTemplate restTemplate) {
        this.greenClient = greenClient;
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getGreenInstanceDetailsFallback")
    public InstanceDetails getInstanceDetails() {
        InstanceDetails greenInstanceDetails = greenClient.getInstanceDetails();

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

    private InstanceDetails getGreenInstanceDetailsFallback() {
        return new InstanceDetails(
                "green",
                "unknown",
                0,
                "some cached config",
                Collections.EMPTY_LIST
        );
    }
}
