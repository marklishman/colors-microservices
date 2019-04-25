package io.lishman.cyan.service;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;

import java.util.List;
import java.util.stream.Collectors;

public class ClientUtils {

    private ClientUtils() { }

    public static <T> List<T> getResourcesContent(Resources<Resource<T>> resources) {
        return resources
                .getContent()
                .stream()
                .map(Resource::getContent)
                .collect(Collectors.toList());
    }

}
