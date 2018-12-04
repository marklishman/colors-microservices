
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

* Service discovery except pink
* Remote config except pink

### blue

* Root

### green

* Instances 3 and 4 'dev' profile (for config)
* Task launcher request (configurable)

### red

* Feign client for geen and orange (no `RestTemplate`)

### orange

* Manual lookup of pink service using config (without Eureka)

### pink

* Not registered with Eureka
* No remote config