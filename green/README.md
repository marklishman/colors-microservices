# Green

## Features

* Spring HATEOAS

---

# Getting Started

### Postgres
* `docker start ref-db`
* Create a schema called `green`
* Run the `scripts/sql/schema.sql` script in this schema.
* Run the `scripts/sql/data.sql` script in this schema.

### Eureka

To enable or disable the Eureka client change this property in `application.yml`

~~~yaml
eureka:
  client:
    enabled: true
~~~

### Elastic Stack

* Use `estack` profile
* Start filebeats
