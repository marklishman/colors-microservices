
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

# Startup

* rabbitmq (docker)
* eureka
* zipkin
* config-server (optional)
* task-launcher (optional)
* get OAuth2 token
* colors

# Instance Details

### blue

* Root
* Config in `@Value`, `application.yml` and config server
* Secure OAuth2 resource server

### green

* Instances 3 and 4 'dev' profile (for config)
* Task launcher request (configurable)

### red

* Feign client for green and orange (no `RestTemplate`)
* Secure config from config server (cipher)
* Secure OAuth2 resource server

### orange

* Manual lookup of 2 pink services using config (without Eureka)

### pink

* Not registered with Eureka
* No remote config