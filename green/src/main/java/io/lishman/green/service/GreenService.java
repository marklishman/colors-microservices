package io.lishman.green.service;

import io.lishman.green.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
@RefreshScope
public class GreenService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default green config}")
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
