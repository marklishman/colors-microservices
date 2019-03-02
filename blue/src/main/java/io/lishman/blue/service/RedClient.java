package io.lishman.blue.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.lishman.blue.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Collections;

@Component
public class RedClient {

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public RedClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @HystrixCommand(fallbackMethod = "getRedInstanceDetailsFallback")
    InstanceDetails getInstanceDetails() {

        final WebClient client = webClientBuilder.build();

        Mono<ClientResponse> result = client
                .get()
                .uri("http://red")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        return result
                .flatMap(res -> res.bodyToMono(InstanceDetails.class))
                .block();
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
