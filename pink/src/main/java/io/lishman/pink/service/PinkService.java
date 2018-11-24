package io.lishman.pink.service;

import io.lishman.pink.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class PinkService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:1}")
    private int instance;

    @Value("${server.port}")
    private int port;

    public InstanceDetails getInstanceDetails() {
        return new InstanceDetails(
                name,
                instance,
                port,
                Collections.emptyList()
        );
    }
}
