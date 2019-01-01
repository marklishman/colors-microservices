# Instances

## Call Tree

            blue
           /    \
      green      red
                /   \
           green     orange
                       |
                      pink
           

## Number of Instances

* blue: 1
* green: 4
* red: 2
* orange: 1
* pink: 2

## Features

### blue

* Root service
* Hystrix (cached fallback)

### green

* Config in `@Value`, `application.yml` and config server
* Instances 3 and 4 'dev' profile (for config)
* `/health` instead of heartbeat for discovery
* Registry not fetched (no services called)
* Explicit `serviceUrl`

### red

* Feign client for green and orange (no `RestTemplate`)
* Secure config from config server (cipher)
* Hystrix enabled automatically by Feign
* Explicit `instanceId` (run config)

### orange

* Manual lookup of 2 pink services using config (without Eureka)
* Hystrix


### pink

* Not registered with Eureka
* No remote config


# Running

* config-server
* eureka
* zipkin
* colors

# Eureka Client

Include the `spring-cloud-starter-netflix-eureka-client` dependency.

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

Add the `@EnableEurekaClient` annotation. 

```typescript
@SpringBootApplication
@EnableEurekaClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

bootstrap properties.

```yaml
spring:
  application:
    name: service-name
```

application properties.

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```