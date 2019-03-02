package io.lishman.blue.service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import io.lishman.blue.model.InstanceDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class GreenClient {

    private InstanceDetails greenInstanceDetails;

    private final WebClient.Builder webClientBuilder;

    @Autowired
    public GreenClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    @HystrixCommand(fallbackMethod = "getGreenInstanceDetailsFallback")
    InstanceDetails getInstanceDetails() {
        final WebClient client = webClientBuilder.build();

        Mono<ClientResponse> result = client
                .get()
                .uri("http://green")
                .accept(MediaType.APPLICATION_JSON)
                .exchange();

        return result
                .flatMap(res -> res.bodyToMono(InstanceDetails.class))
                .block();
    }

    private InstanceDetails getGreenInstanceDetailsFallback() {
        if (greenInstanceDetails == null) {
            greenInstanceDetails = new InstanceDetails();
        }
        greenInstanceDetails.setInstance("unknown");
        greenInstanceDetails.setPort(0);
        return greenInstanceDetails;
    }
}
