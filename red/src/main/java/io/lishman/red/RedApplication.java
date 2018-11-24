package io.lishman.red;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient
public class RedApplication {

	public static void main(String[] args) {
		SpringApplication.run(RedApplication.class, args);
	}
}
