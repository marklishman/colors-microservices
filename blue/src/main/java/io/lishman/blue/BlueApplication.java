package io.lishman.blue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class BlueApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlueApplication.class, args);
    }
}
