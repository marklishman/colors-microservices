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


# Running the Application

* config-server
* eureka
* zipkin
* colors

# Eureka & Ribbon

Include the `spring-cloud-starter-netflix-eureka-client` dependency.

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-eureka-client</artifactId>
</dependency>
```

Add the `@EnableEurekaClient` annotation. 

```java
@SpringBootApplication
@EnableEurekaClient
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Set the service name in `bootstrap.yml`

```yaml
spring:
  application:
    name: service-name
```

Set the location of the Eureka server in `application.yml`

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
```

# Hystrix

Include the `spring-cloud-starter-netflix-hystrix` dependency.

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-netflix-hystrix</artifactId>
</dependency>
```

Add the `@EnableCircuitBreaker` annotation. 

```java
@SpringBootApplication
@EnableCircuitBreaker
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

Allow the hystrix data to be accessed by enabling the actuator endpoint in `application.yml`

```yaml
management:
  endpoints:
    web:
      exposure:
        include: hystrix.stream
```

Add the `@LoadBalanced` annotation to the `RestTemplate` bean.

```java
@Configuration
public class RestConfig {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder().build();
    }
}
```

# Sleuth & Zipkin

Include the `spring-cloud-starter-zipkin` dependency.

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-zipkin</artifactId>
</dependency>
```

Set the sample rate in `application.yml`

```yaml
spring:
  sleuth:
    sampler:
      probability: 1.0
```

# Config

Include the `spring-cloud-starter-config` dependency.

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-config</artifactId>
</dependency>
```

Set the location of the Config server and the symmetric encryption key in `bootstrap.yml`

```yaml
spring:
  cloud:
    config:
      uri: http://localhost:8888 # default

encrypt:
  key: my_secret_symmetric_encryption_key
```

Allow config data to be refreshed by enabling the actuator endpoint in `application.yml`

```yaml
management:
  endpoints:
    web:
      exposure:
        include: refresh
```

Add the `@RefreshScope` annotation to class which uses the config values.

```java
@Service
@RefreshScope
public class MyService {

    @Value("${spring.application.name}")
    private String name;
```

To refresh the config data do an HTTP Post to the `refresh` endpoint. For example

    localhost:8021/actuator/refresh