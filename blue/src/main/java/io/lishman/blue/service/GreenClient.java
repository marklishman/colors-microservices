package io.lishman.blue.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.lishman.blue.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class GreenClient {

    private final RestTemplate restTemplate;

    private InstanceDetails greenInstanceDetails;

    @Autowired
    public GreenClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getGreenInstanceDetailsFallback")
    InstanceDetails getInstanceDetails() {
        greenInstanceDetails = restTemplate.getForObject(
                "http://green",
                InstanceDetails.class
        );

        return greenInstanceDetails;
    }

    private InstanceDetails getGreenInstanceDetailsFallback() {
        greenInstanceDetails.setInstance("unknown");
        greenInstanceDetails.setPort(0);
        return greenInstanceDetails;
    }
}
