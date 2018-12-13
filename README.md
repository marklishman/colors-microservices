
# Dependencies

* Spring Boot 2.0.6
* Spring Cloud Finchley.SR2

# Call Tree

            blue
           /    \
      green      red
                /   \
           green     orange
                       |
                      pink
           

# Number of Instances

* blue: 1
* green: 4
* red: 2
* orange: 1
* pink: 2

# Instance Details

### blue

* Root
* Hystrix

### green

* Config in `@Value`, `application.yml` and config server
* Instances 3 and 4 'dev' profile (for config)
* `/health` instead of heartbeat for discovery
* Registry not fetched (no services called)

### red

* Feign client for green and orange (no `RestTemplate`)
* Secure config from config server (cipher)
* Secure OAuth2 resource server
* Hystrix enabled automatically by Feign

### orange

* Manual lookup of 2 pink services using config (without Eureka)
* Hystrix

### pink

* Not registered with Eureka
* No remote config


# Full Startup

* eureka
* zipkin
* config-server
* colors
