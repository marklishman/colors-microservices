package io.lishman.green.service;

import io.lishman.green.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Collections;

@Service
public class GreenService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance.name:0}")
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
