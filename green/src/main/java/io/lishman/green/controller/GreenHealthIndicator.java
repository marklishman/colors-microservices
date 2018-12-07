package io.lishman.green.controller;

import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreenHealthIndicator implements HealthIndicator {

    private String health = "up";

    @PostMapping("/")
    public void setHealth(@RequestBody String health) {
        this.health = health;
    }

    @Override
    public Health health() {
        if (health.equalsIgnoreCase("up")) {
            return Health.up().build();
        }
        return Health.down()
                .withDetail("Error Code", 999)
                .build();
    }

}