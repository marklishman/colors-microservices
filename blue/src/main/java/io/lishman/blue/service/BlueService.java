package io.lishman.blue.service;

import io.lishman.blue.model.InstanceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RefreshScope
public class BlueService {

    private static final Logger LOGGER = LoggerFactory.getLogger(BlueService.class);

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default blue config}")
    private String config;

    private final GreenClient greenClient;
    private final RedClient redClient;

    @Autowired
    public BlueService(GreenClient greenClient, RedClient redClient) {
        this.greenClient = greenClient;
        this.redClient = redClient;
    }

    public InstanceDetails getInstanceDetails() {

        InstanceDetails greenInstanceDetails = greenClient.getInstanceDetails();
        InstanceDetails redInstanceDetails = redClient.getInstanceDetails();

        InstanceDetails instanceDetails = new InstanceDetails(
                name,
                instance,
                port,
                config,
                Arrays.asList(greenInstanceDetails, redInstanceDetails));
        LOGGER.info(instanceDetails.toString());
        return instanceDetails;
    }
}
