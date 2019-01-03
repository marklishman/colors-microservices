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

* Root application
* List of 'red' instances retrieved programmatically
* Hystrix with cached fallback value

### green

* `/health` instead of heartbeat for discovery
* Registry not fetched from Eureka (no downstream applications called)
* Config values in `@Value`, `application.yml` and config server
* Instances 3 and 4 'dev' profile (for config)

### red

* Explicit `instanceId` (run config)
* Feign client for green and orange (no `RestTemplate`)
* Hystrix enabled automatically by Feign
* Secure config from config server (cipher)

### orange

* Manual lookup of 2 pink microservices using config (without Eureka)
* Hystrix with empty object


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

This will include the Ribbon dependency also.

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

Set the application name in `bootstrap.yml`

```yaml
spring:
  application:
    name: app-name
```

Set the location of the Eureka server in `application.yml`

```yaml
eureka:
  client:
    serviceUrl:
      defaultZone: http://localhost:8761/eureka/
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

And include the application name in the `RestTemplate` URL

```java
return restTemplate.getForObject(
        "http://app-name",
        InstanceDetails.class
);
```

Or to access the registry programatically

```java
@Autowired
private DiscoveryClient discoveryClient;

public List<String> getInstances() {
    List<ServiceInstance> list = discoveryClient.getInstances("service");
    ...
}
```

From the documentation
* [Get the next server from Eureka programmatically](https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.0.2.RELEASE/single/spring-cloud-netflix.html#_using_the_eurekaclient)
* [Get a list of servers from Eureka programmatically](https://cloud.spring.io/spring-cloud-static/spring-cloud-netflix/2.0.2.RELEASE/single/spring-cloud-netflix.html#_alternatives_to_the_native_netflix_eurekaclient)

### Health

To use the `/heath` endpoint rather than the heartbeat, first add a `HealthIndicator`

```java
@Component
public class CustomHealthIndicator implements HealthIndicator {

    @Override
    public Health health() {
        if (everythingOk) {
            return Health.up().build();
        }
        return Health.down()
                .withDetail("Error Code", 999)
                .build();
    }
}
```

Expose the actuator `/health` endpoint and enable the health check in `application.yml`

```yaml
eureka:
  client:
    healthcheck:
      enabled: true

management:
  endpoints:
    web:
      exposure:
        include: health
```

### Ribbon without Eureka

To configure Ribbon without using the Eureka registry add these properties to application.yml

```yaml
ribbon:
  eureka:
    enabled: false

client-config:
  ribbon:
    listOfServers: localhost:8051,localhost:8052
``` 

and include the `@RibbonClient` annotation

```java
@SpringBootApplication
@RibbonClient(name = "client-config"
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

use this name in the `RestTemplate`

```java
restTemplate.getForObject( "http://client-config", String.class );
```

To add extra configuration values such as `ILoadBalancer`, `ServerListFilter`, `IRule`

    @RibbonClient(name = "client-config", configuration = RestConfig.class)


```java
@Configuration
public class RibbonConfig {

    @Bean
    public IPing ribbonPing() {
        return new PingUrl();
    }
}
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

Add the `@HystrixCommand` annotation and provide the fallback method.

```java
    @HystrixCommand(fallbackMethod = "getDataFallback")
    MyData getData() {
        ...
    }

    private MyData getDataFallback() {
        return new MyData();
    }
```

# Feign

Include the `spring-cloud-starter-openfeign` dependency.

```xml
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-starter-openfeign</artifactId>
</dependency>
```

Add the `@EnableFeignClients` annotation.

```java
@SpringBootApplication
@EnableFeignClients
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
```

To enable hystrix with feign include this property in `application.yml`

```yaml
feign:
  hystrix:
    enabled: true
```

Implement the Feign client using the `@FeignClient` annotation

```java
@FeignClient(value = "Orange")
public interface OrangeClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    InstanceDetails getDetails();
}
```

and to include hystrix

```java
@FeignClient(value = "Orange", fallback = OrangeClientFallback.class)
public interface OrangeClient {

    @RequestMapping(method = RequestMethod.GET, value = "/")
    InstanceDetails getDetails();

    @Component
    static class OrangeClientFallback implements OrangeClient {

        @Override
        public InstanceDetails getDetails() {
            return new InstanceDetails(
                    "orange",
                    "unknown",
                    0,
                    "some fallback config",
                    Collections.EMPTY_LIST
            );
        }
    }

}
```

Now we can just use the method like this

    InstanceDetails orangeInstanceDetails = orangeClient.getDetails();


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
      uri: http://localhost:8888

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