package io.lishman.cyan.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.client.Traverson;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.ExchangeStrategies;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;
import java.net.URISyntaxException;

@Configuration
public class RestConfig {

    // ~~~~ RestTemplate

    @Bean
    public RestTemplate purpleRestTemplate() {
        return new RestTemplateBuilder()
                .rootUri("http://localhost:8061/purple")
                .build();
    }

    // ~~~~ WebClient

    @Bean
    public WebClient whiteWebClient() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:8071")
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient greenWebClient() {
        return greenWebClientBuilder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
                .build();
    }

    @Bean
    public WebClient greenHalWebClient() {

        final ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.registerModule(new Jackson2HalModule());

        final ExchangeStrategies strategies = ExchangeStrategies
                .builder()
                .codecs(clientDefaultCodecsConfigurer -> {
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonEncoder(new Jackson2JsonEncoder(new ObjectMapper(), MediaType.APPLICATION_JSON));
                    clientDefaultCodecsConfigurer.defaultCodecs().jackson2JsonDecoder(new Jackson2JsonDecoder(mapper, MediaType.APPLICATION_JSON, MediaTypes.HAL_JSON));
                }).build();

        return greenWebClientBuilder()
                .defaultHeader(HttpHeaders.ACCEPT, MediaTypes.HAL_JSON_VALUE)
                .exchangeStrategies(strategies)
                .build();
    }


    // ~~~~ Traverson

    @Bean
    public Traverson purpleTraverson() {
        return new Traverson(getUri("http://localhost:8061/purple/api"), MediaTypes.HAL_JSON);
    }

    // ~~~~ private

    private WebClient.Builder greenWebClientBuilder() {
        return WebClient
                .builder()
                .baseUrl("http://localhost:8021");
    }

    private URI getUri(final String uri) {
        try {
            return new URI(uri);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }
}
