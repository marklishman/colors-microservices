
# Dependencies

* Spring Boot 2.0.6
* Spring Cloud Finchley.SR2

# Call Hierarchy

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
* Secure OAuth2 resource server

### green

* Config in `@Value`, `application.yml` and config server
* Instances 3 and 4 'dev' profile (for config)
* `/health` instead of heartbeat for discovery
* Registry not fetched (no services called)

### red

* Feign client for green and orange (no `RestTemplate`)
* Secure config from config server (cipher)
* Secure OAuth2 resource server

### orange

* Manual lookup of 2 pink services using config (without Eureka)

### pink

* Not registered with Eureka
* No remote config


# Full Startup

* rabbitmq
* eureka
* zipkin
* config-server
* task-launcher
* (get OAuth2 token)
* colors
