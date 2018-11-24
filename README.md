
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
           

# Instances

* blue: 1
* green: 4
* red: 2
* orange: 1
* pink: 2 (no service discovery)