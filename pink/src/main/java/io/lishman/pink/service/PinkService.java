package io.lishman.pink.service;

import io.lishman.pink.model.InstanceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PinkService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PinkService.class);

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default pink config}")
    private String config;

    public InstanceDetails getInstanceDetails() {
        InstanceDetails instanceDetails = new InstanceDetails(
                name,
                instance,
                port,
                config,
                Collections.emptyList()
        );
        LOGGER.info(instanceDetails.toString());
        return instanceDetails;
    }
}
