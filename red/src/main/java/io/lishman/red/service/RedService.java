package io.lishman.red.service;

import io.lishman.red.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class RedService {

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    private final GreenClient greenClient;
    private final OrangeClient orangeClient;

    @Autowired
    public RedService(
            GreenClient greenClient,
            OrangeClient orangeClient
    ) {
        this.greenClient = greenClient;
        this.orangeClient = orangeClient;
    }

    public InstanceDetails getInstanceDetails() {

        InstanceDetails greenInstanceDetails = greenClient.getDetails();
        InstanceDetails orangeInstanceDetails = orangeClient.getDetails();

        return new InstanceDetails(
                name,
                instance,
                port,
                Arrays.asList(greenInstanceDetails, orangeInstanceDetails)
        );
    }
}
