package io.lishman.orange.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.lishman.orange.model.InstanceDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Service
@RefreshScope
public class OrangeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrangeService.class);

    @Value("${spring.application.name}")
    private String name;

    @Value("${app.instance:one}")
    private String instance;

    @Value("${server.port}")
    private int port;

    @Value("${app.config:default orange config}")
    private String config;

    private final RestTemplate restTemplate;

    @Autowired
    public OrangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getInstanceDetailsFallback")
    public InstanceDetails getInstanceDetails() {

        InstanceDetails pinkInstanceDetails = restTemplate.getForObject(
                "http://pinkClient",
                InstanceDetails.class
        );

        InstanceDetails instanceDetails = new InstanceDetails(
                name,
                instance,
                port,
                config,
                Collections.singletonList(pinkInstanceDetails)
        );
        LOGGER.info(instanceDetails.toString());
        return instanceDetails;
    }

    private InstanceDetails getInstanceDetailsFallback() {
        return new InstanceDetails(
                name,
                instance,
                port,
                config,
                Collections.emptyList()
        );
    }
}
