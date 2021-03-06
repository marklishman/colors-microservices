package io.lishman.red.service;

import io.lishman.red.model.InstanceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
@RefreshScope
public class RedService {

    private static final Logger LOGGER = LoggerFactory.getLogger(RedService.class);

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default red config}")
    private String config;

    @Value("${app.secret:default red secret config}")
    private String secretConfig;

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

        InstanceDetails instanceDetails = new InstanceDetails(
                name,
                instance,
                port,
                config + ", " + secretConfig,
                Arrays.asList(greenInstanceDetails, orangeInstanceDetails)
        );
        LOGGER.info(instanceDetails.toString());
        return instanceDetails;
    }
}
