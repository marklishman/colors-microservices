package io.lishman.red.service;

import io.lishman.red.model.InstanceDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Collections;

import static io.lishman.red.service.OrangeClient.OrangeClientFallback;

@FeignClient(value = "Orange", fallback = OrangeClientFallback.class)
public interface OrangeClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    InstanceDetails getDetails();

    @Component
    static class OrangeClientFallback implements OrangeClient {
        @Override
        public InstanceDetails getDetails() {
            // TODO check values
            return new InstanceDetails(
                    "orange",
                    "unknown",
                    0,
                    "config",
                    Collections.EMPTY_LIST
            );
        }
    }

}