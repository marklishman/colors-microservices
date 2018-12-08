package io.lishman.blue.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.lishman.blue.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class GreenClient {

    private final RestTemplate restTemplate;

    @Autowired
    public GreenClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getGreenInstanceDetailsFallback")
    public InstanceDetails getInstanceDetails() {
        return restTemplate.getForObject(
                "http://green",
                InstanceDetails.class
        );
    }

    private InstanceDetails getGreenInstanceDetailsFallback() {
        return new InstanceDetails(
                "green",
                "unknown",
                0,
                "some cached config",
                Collections.EMPTY_LIST
        );
    }
}
