package io.lishman.red.service;

import io.lishman.red.model.InstanceDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;

import static io.lishman.red.service.GreenClient.GreenClientFallback;

@FeignClient(value = "green", fallback = GreenClientFallback.class)
public interface GreenClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    InstanceDetails getDetails();

    @Component
    static class GreenClientFallback implements GreenClient {

        @Override
        public InstanceDetails getDetails() {
            return new InstanceDetails(
                    "green",
                    "unknown",
                    0,
                    "some cached config",
                    Collections.EMPTY_LIST
            );
        }
    }

}