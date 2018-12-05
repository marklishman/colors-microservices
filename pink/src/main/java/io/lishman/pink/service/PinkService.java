package io.lishman.pink.service;

import io.lishman.pink.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PinkService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${token.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${token.config:default pink config}")
    private String config;

    public InstanceDetails getInstanceDetails() {
        return new InstanceDetails(
                name,
                instance,
                port,
                config,
                Collections.emptyList()
        );
    }
}
