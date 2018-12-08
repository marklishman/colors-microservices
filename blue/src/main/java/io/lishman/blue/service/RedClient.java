package io.lishman.blue.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.lishman.blue.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

@Component
public class RedClient {

    private final RestTemplate restTemplate;

    @Autowired
    public RedClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @HystrixCommand(fallbackMethod = "getRedInstanceDetailsFallback")
    InstanceDetails getInstanceDetails() {
        return restTemplate.getForObject(
                "http://red",
                InstanceDetails.class
        );
    }

    private InstanceDetails getRedInstanceDetailsFallback() {
        return new InstanceDetails(
                "red",
                "unknown",
                0,
                "some cached config",
                Collections.EMPTY_LIST
        );
    }
}
