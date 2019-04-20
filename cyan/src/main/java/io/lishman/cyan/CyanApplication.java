package io.lishman.cyan;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.hateoas.config.EnableHypermediaSupport;

/**
 * @EnableHypermediaSupport
 *
 * Activates hypermedia support in the ApplicationContext. Will register infrastructure beans available for injection
 * to ease building hypermedia related code. Which components get registered depends on the hypermedia type being
 * activated through the type() attribute. Hypermedia-type-specific implementations of the following components
 * will be registered:
 * 1. LinkDiscoverer
 * 2. a Jackson (1 or 2, depending on what is on the classpath) module to correctly marshal the resource model
 * classes into the appropriate representation.
 */

@SpringBootApplication
@EnableHypermediaSupport(type = EnableHypermediaSupport.HypermediaType.HAL)
@EnableFeignClients
public class CyanApplication {

    public static void main(String[] args) {
        SpringApplication.run(CyanApplication.class, args);
    }
}
