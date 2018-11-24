package io.lishman.red.service;

import io.lishman.red.model.InstanceDetails;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("green")
public interface GreenClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    InstanceDetails getDetails();

}