package io.lishman.orange;

import io.lishman.orange.config.RestConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@RibbonClient(name = "pink-client", configuration = RestConfig.class)
public class OrangeApplication {

	public static void main(String[] args) {
		SpringApplication.run(OrangeApplication.class, args);
	}
}
