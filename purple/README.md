# Purple

## Project Features

* Spring Data REST
* HATEOAS

---

# Spring Data REST Features

* Discoverability, Hypermedia, Links
* Collections, items, and sub-resources
* Projections, excerpts
* Paging, sorting
* Object graph
* Search
* CRUD, associations
* Application Events
* Custom controllers
* Security
* Metadata - ALPS, JSON Schema
* Traverson
* HAL Browser

From the Spring Data REST page. 

* Exposes a discoverable REST API for your domain model using HAL as media type.
* Exposes collection, item and association resources representing your model.
* Supports pagination via navigational links.
* Allows to dynamically filter collection resources.
* Exposes dedicated search resources for query methods defined in your repositories.
* Allows to hook into the handling of REST requests by handling Spring ApplicationEvents.
* Exposes metadata about the model discovered as ALPS and JSON Schema.
* Allows to define client specific representations through projections.
* Ships a customized variant of the HAL Browser to leverage the exposed metadata.
* Currently supports JPA, MongoDB, Neo4j, Solr, Cassandra, Gemfire.
* Allows advanced customizations of the default resources exposed.

---

# Schema

<img src="database-schema.png" width="700" height="800"/>

---

# Repositories

We use standard Spring Data JPA repositories with a couple of extra annotations for Spring Data REST.

## Group

~~~java
@RepositoryRestResource (
        path = "groups",
        collectionResourceRel = "groups",
        collectionResourceDescription = @Description("A collection of groups"),
        itemResourceRel = "group",
        itemResourceDescription = @Description("A single group")
)
public interface GroupRepository extends JpaRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByName(final String name);

}
~~~

## Item

~~~java
@RepositoryRestResource (
        path = "items",
        collectionResourceRel = "items",
        collectionResourceDescription = @Description("A collection of items"),
        itemResourceRel = "item",
        itemResourceDescription = @Description("A single item")
)
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByUuid(final UUID id);

    List<ItemEntity> findByCorrelationId(final UUID correlationId);

    @RestResource(path = "findByGroupName", rel = "findByGroupName")
    List<ItemEntity> findByGroupNameContainingIgnoreCase(final String groupNameContains, final Pageable pageable);

}
~~~

# Resource Discoverability

Use the API root to see which resource URLs are available.

    http://localhost:8061/purple/api

~~~json
{
  "_links": {
    "countries": {
      "href": "http://localhost:8061/purple/api/countries{?page,size,sort}",
      "templated": true
    },
    "categories": {
      "href": "http://localhost:8061/purple/api/categories{?projection}",
      "templated": true
    },
    "people": {
      "href": "http://localhost:8061/purple/api/people{?page,size,sort,projection}",
      "templated": true
    },
    "items": {
      "href": "http://localhost:8061/purple/api/items{?page,size,sort,projection}",
      "templated": true
    },
    "groups": {
      "href": "http://localhost:8061/purple/api/groups{?page,size,sort}",
      "templated": true
    },
    "profile": {
      "href": "http://localhost:8061/purple/api/profile"
    }
  }
}
~~~

# Collection Resources

We can retrieve a collections of resources

    http://localhost:8061/purple/api/groups

~~~json
{
  "_embedded": {
    "groups": [
      {
        "name": "Group One",
        "description": "Group one description",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/groups/1"
          },
          "group": {
            "href": "http://localhost:8061/purple/api/groups/1"
          },
          "items": {
            "href": "http://localhost:8061/purple/api/groups/1/items{?projection}",
            "templated": true
          }
        }
      },
      {
        "name": "Group Four",
        "description": "Group four description",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/groups/4"
          },
          "group": {
            "href": "http://localhost:8061/purple/api/groups/4"
          },
          "items": {
            "href": "http://localhost:8061/purple/api/groups/4/items{?projection}",
            "templated": true
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/groups{?page,size,sort}",
      "templated": true
    },
    "profile": {
      "href": "http://localhost:8061/purple/api/profile/groups"
    },
    "search": {
      "href": "http://localhost:8061/purple/api/groups/search"
    }
  },
  "page": {
    "size": 20,
    "totalElements": 4,
    "totalPages": 1,
    "number": 0
  }
}
~~~

# Item Resources

or a single item.

    http://localhost:8061/purple/api/groups/3

~~~json    
{
  "name": "Group Three",
  "description": "Group three description",
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/groups/3"
    },
    "group": {
      "href": "http://localhost:8061/purple/api/groups/3"
    },
    "items": {
      "href": "http://localhost:8061/purple/api/groups/3/items{?projection}",
      "templated": true
    }
  }
}
~~~

# Object Graph

Note that the result above is limited to the `groups` resource only.<br/>
However, the `items` resource includes `data` resources, and a link to `category` where relevant

    http://localhost:8061/purple/api/items/4

~~~json
{
  "uuid": "4b30d7c8-2f17-49da-bff9-3a04364c5a08",
  "name": "Item Four",
  "description": "Item four description",
  "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
  "status": 3,
  "createdAt": "2019-03-29T16:00:26.676794",
  "data": [
    {
      "value": 111.43,
      "createdAt": "2019-03-29T16:00:27.126485",
      "_links": {
        "item": {
          "href": "http://localhost:8061/purple/api/items/4{?projection}",
          "templated": true
        },
        "category": {
          "href": "http://localhost:8061/purple/api/categories/3{?projection}",
          "templated": true
        }
      }
    },
    {
      "value": 7.43,
      "createdAt": "2019-03-29T16:00:27.159121",
      "_links": {
        "item": {
          "href": "http://localhost:8061/purple/api/items/4{?projection}",
          "templated": true
        }
      }
    }
  ],
  "total": 118.86,
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/items/4"
    },
    "item": {
      "href": "http://localhost:8061/purple/api/items/4{?projection}",
      "templated": true
    },
    "group": {
      "href": "http://localhost:8061/purple/api/items/4/group"
    }
  }
}
~~~

From the Spring Data REST documentation.

> Spring Data REST tries very hard to render your object graph correctly. It tries to serialize 
unmanaged beans as normal POJOs, and it tries to create links to managed beans where necessary.

# Sub-Resources

> Spring Data REST exposes sub-resources of every item resource 
for each of the associations the item resource has.

    http://localhost:8061/purple/api/groups/2/items/4
    
~~~json
{
  "uuid": "4b30d7c8-2f17-49da-bff9-3a04364c5a08",
  "name": "Item Four",
  "description": "Item four description",
  "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
  "status": 3,
  "createdAt": "2019-03-29T16:00:26.676794",
  "data": [
    {
      "value": 111.43,
      "createdAt": "2019-03-29T16:00:27.126485",
      "_links": {
        "item": {
          "href": "http://localhost:8061/purple/api/items/4{?projection}",
          "templated": true
        },
        "category": {
          "href": "http://localhost:8061/purple/api/categories/3{?projection}",
          "templated": true
        }
      }
    },
    {
      "value": 7.43,
      "createdAt": "2019-03-29T16:00:27.159121",
      "_links": {
        "item": {
          "href": "http://localhost:8061/purple/api/items/4{?projection}",
          "templated": true
        }
      }
    }
  ],
  "total": 118.86,
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/items/4"
    },
    "item": {
      "href": "http://localhost:8061/purple/api/items/4{?projection}",
      "templated": true
    },
    "group": {
      "href": "http://localhost:8061/purple/api/items/4/group"
    }
  }
}
~~~
    
# Projections

Projections alter the view of the returned data.

### Subset

This projection returns the item name and description only

~~~java
@Projection(name = "name", types = { ItemEntity.class })
public interface ItemNameProjection {

    String getName();
    String getDescription();

}
~~~

    http://localhost:8061/purple/api/items/1?projection=name
    
returns a subset of the `item` resource

~~~json
{
  "name": "Item One",
  "description": "Item one description",
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/items/1"
    },
    "item": {
      "href": "http://localhost:8061/purple/api/items/1{?projection}",
      "templated": true
    },
    "group": {
      "href": "http://localhost:8061/purple/api/items/1/group"
    }
  }
}

~~~

### Using SpEL

~~~java
@Projection(name = "name", types = { PersonEntity.class })
public interface PersonNameProjection {

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

}
~~~

    http://localhost:8061/purple/api/people/1?projection=name
    
~~~json
{
  "fullName": "Bob Jones",
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/people/1"
    },
    "person": {
      "href": "http://localhost:8061/purple/api/people/1{?projection}",
      "templated": true
    },
    "countries": {
      "href": "http://localhost:8061/purple/api/people/1/countries"
    }
  }
}
~~~

### Superset

~~~java
@Projection(name = "full", types = { ItemEntity.class })
public interface ItemFullProjection {
    
    String getId();
    UUID getUuid();
    String getName();
    String getDescription();
    String getCorrelationId();
    Integer getStatus();
    LocalDateTime getCreatedAt();
    List<DataFullProjection> getData();
    
}
~~~

and

~~~java
@Projection(name = "full", types = { DataEntity.class })
public interface DataFullProjection {

    String getId();
    BigDecimal getValue();
    LocalDateTime getCreatedAt();
    CategoryNameProjection getCategory();

}
~~~

and

~~~java
@Projection(name = "name", types = { CategoryEntity.class })
public interface CategoryNameProjection {

    @Value("#{target.name} - #{target.description}")
    String getDetails();

}
~~~

produces

    http://localhost:8061/purple/api/items/4?projection=full

~~~json
{
  "name": "Item Four",
  "id": "4",
  "uuid": "4b30d7c8-2f17-49da-bff9-3a04364c5a08",
  "description": "Item four description",
  "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
  "status": 3,
  "createdAt": "2019-03-29T16:00:26.676794",
  "data": [
    {
      "value": 111.43,
      "id": "15",
      "createdAt": "2019-03-29T16:00:27.126485",
      "category": {
        "details": "Category Three - Category three description",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/categories/3{?projection}",
            "templated": true
          }
        }
      }
    },
    {
      "value": 7.43,
      "id": "16",
      "createdAt": "2019-03-29T16:00:27.159121",
      "category": null
    }
  ],
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/items/4"
    },
    "item": {
      "href": "http://localhost:8061/purple/api/items/4{?projection}",
      "templated": true
    },
    "group": {
      "href": "http://localhost:8061/purple/api/items/4/group"
    }
  }
}
~~~

Note that the `CategoryNameProjection` is used to include category details. Category is usually
excluded because it is a managed resource.

Also, `id` fields are omitted by default. However, we have chosen to include them here by
explicitly specifying them in the projection. 

# Excerpts

An excerpt is a projection that is automatically applied to a resource collection.

~~~java
@Projection(name = "name", types = { PersonEntity.class })
public interface PersonNameProjection {

    @Value("#{target.firstName} #{target.lastName}")
    String getFullName();

}
~~~

~~~java
@RepositoryRestResource (
        path = "people",
        collectionResourceRel = "people",
        collectionResourceDescription = @Description("A collection of people"),
        itemResourceRel = "person",
        itemResourceDescription = @Description("A single person"),
        excerptProjection = PersonNameProjection.class
)
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {

    @RestResource(path = "findByLastName")
    List<PersonEntity> findByLastNameContainingIgnoreCase(final String nameContains);

}
~~~

Projection is automatically applied

    http://localhost:8061/purple/api/categories    

~~~json
{
  "_embedded": {
    "people": [
      {
        "fullName": "Bob Jones",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/people/1"
          },
          "person": {
            "href": "http://localhost:8061/purple/api/people/1{?projection}",
            "templated": true
          },
          "countries": {
            "href": "http://localhost:8061/purple/api/people/1/countries"
          }
        }
      },
      {
        "fullName": "Sally Smith",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/people/4"
          },
          "person": {
            "href": "http://localhost:8061/purple/api/people/4{?projection}",
            "templated": true
          },
          "countries": {
            "href": "http://localhost:8061/purple/api/people/4/countries"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/people{?page,size,sort,projection}",
      "templated": true
    },
    "profile": {
      "href": "http://localhost:8061/purple/api/profile/people"
    },
    "search": {
      "href": "http://localhost:8061/purple/api/people/search"
    }
  },
  "page": {
    "size": 20,
    "totalElements": 4,
    "totalPages": 1,
    "number": 0
  }
}
~~~

# Search

The `ItemRepository` looks like this.

~~~java
@RepositoryRestResource (
        path = "items",
        collectionResourceRel = "items",
        collectionResourceDescription = @Description("A collection of items"),
        itemResourceRel = "item",
        itemResourceDescription = @Description("A single item")
)
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByUuid(final UUID id);

    List<ItemEntity> findByCorrelationId(final UUID correlationId);

    @RestResource(path = "findByGroupName", rel = "findByGroupName")
    List<ItemEntity> findByGroupNameContainingIgnoreCase(final String groupNameContains, final Pageable pageable);

}
~~~

When we use the `/search` path

    http://localhost:8061/purple/api/items/search
    
we get a list of the query methods on the resource repository.

~~~json
{
  "_links": {
    "findByGroupName": {
      "href": "http://localhost:8061/purple/api/items/search/findByGroupName{?groupNameContains,page,size,sort,projection}",
      "templated": true
    },
    "findByCorrelationId": {
      "href": "http://localhost:8061/purple/api/items/search/findByCorrelationId{?correlationId,projection}",
      "templated": true
    },
    "findByUuid": {
      "href": "http://localhost:8061/purple/api/items/search/findByUuid{?id,projection}",
      "templated": true
    },
    "self": {
      "href": "http://localhost:8061/purple/api/items/search"
    }
  }
}
~~~

    http://localhost:8061/purple/api/items/search/findByGroupName?groupNameContains=three
    
~~~json
{
  "_embedded": {
    "items": [
      {
        "uuid": "fb8d5122-dfcd-4509-a99e-222e862a1658",
        "name": "Item Seven",
        "description": "Item seven description",
        "correlationId": "e4b4a967-3758-4479-9a26-7ed5608f978a",
        "status": 3,
        "createdAt": "2019-03-29T16:00:26.745543",
        "data": [
          {
            "value": 32.45,
            "createdAt": "2019-03-29T16:00:27.303717",
            "_links": {
              "item": {
                "href": "http://localhost:8061/purple/api/items/7{?projection}",
                "templated": true
              },
              "category": {
                "href": "http://localhost:8061/purple/api/categories/3{?projection}",
                "templated": true
              }
            }
          }
        ],
        "total": 32.45,
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/items/7"
          },
          "item": {
            "href": "http://localhost:8061/purple/api/items/7{?projection}",
            "templated": true
          },
          "group": {
            "href": "http://localhost:8061/purple/api/items/7/group"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/items/search/findByGroupName?groupNameContains=three"
    }
  }
}
~~~

# Paging and Sorting

~~~java
@RepositoryRestResource (
        path = "countries",
        collectionResourceRel = "countries",
        collectionResourceDescription = @Description("A collection of countries"),
        itemResourceRel = "country",
        itemResourceDescription = @Description("A single country")
)
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    @RestResource(path = "findByName")
    List<CountryEntity> findByNameContainingIgnoreCase(final String nameContains);

    @RestResource(exported = false)
    Optional<CountryEntity> findByCode(final String code);

    @RestResource(exported = false)
    @Override
    void deleteById(Long id);

    @RestResource(exported = false)
    @Override
    void delete(CountryEntity countryEntity);

    @RestResource(exported = false)
    @Override
    void deleteAll(Iterable<? extends CountryEntity> var1);

}
~~~

Note that `JpaRepository` extends `PagingAndSortingRepository`

    http://localhost:8061/purple/api/countries?page=5&size=3&sort=code,desc
    
~~~json
{
  "_embedded": {
    "countries": [
      {
        "code": "US",
        "name": "United States",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/countries/233"
          },
          "country": {
            "href": "http://localhost:8061/purple/api/countries/233"
          },
          "people": {
            "href": "http://localhost:8061/purple/api/countries/233/people{?projection}",
            "templated": true
          }
        }
      },
      {
        "code": "UM",
        "name": "United States Minor Outlying Islands",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/countries/234"
          },
          "country": {
            "href": "http://localhost:8061/purple/api/countries/234"
          },
          "people": {
            "href": "http://localhost:8061/purple/api/countries/234/people{?projection}",
            "templated": true
          }
        }
      },
      {
        "code": "UG",
        "name": "Uganda",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/countries/229"
          },
          "country": {
            "href": "http://localhost:8061/purple/api/countries/229"
          },
          "people": {
            "href": "http://localhost:8061/purple/api/countries/229/people{?projection}",
            "templated": true
          }
        }
      }
    ]
  },
  "_links": {
    "first": {
      "href": "http://localhost:8061/purple/api/countries?page=0&size=3&sort=code,desc"
    },
    "prev": {
      "href": "http://localhost:8061/purple/api/countries?page=4&size=3&sort=code,desc"
    },
    "self": {
      "href": "http://localhost:8061/purple/api/countries"
    },
    "next": {
      "href": "http://localhost:8061/purple/api/countries?page=6&size=3&sort=code,desc"
    },
    "last": {
      "href": "http://localhost:8061/purple/api/countries?page=81&size=3&sort=code,desc"
    },
    "profile": {
      "href": "http://localhost:8061/purple/api/profile/countries"
    },
    "search": {
      "href": "http://localhost:8061/purple/api/countries/search"
    }
  },
  "page": {
    "size": 3,
    "totalElements": 245,
    "totalPages": 82,
    "number": 5
  }
}
~~~

Note the next and previous page links.


Paging can be included on some query methods and not others.

~~~java
@RepositoryRestResource (
        path = "items",
        collectionResourceRel = "items",
        collectionResourceDescription = @Description("A collection of items"),
        itemResourceRel = "item",
        itemResourceDescription = @Description("A single item")
)
public interface ItemRepository extends JpaRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByUuid(final UUID id);

    List<ItemEntity> findByCorrelationId(final UUID correlationId);

    @RestResource(path = "findByGroupName", rel = "findByGroupName")
    List<ItemEntity> findByGroupNameContainingIgnoreCase(final String groupNameContains, final Pageable pageable);

}
~~~


And putting it all together.

* search
* pagination
* sorting
* projection

~~~
http://localhost:8061/purple/api/items/search/findByGroupName?groupNameContains=gro&page=1&size=3&sort=name,desc&projection=totals
~~~

~~~json
{
  "_embedded": {
    "items": [
      {
        "name": "Item Seven",
        "total": 32.45,
        "description": "Item seven description",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/items/7"
          },
          "item": {
            "href": "http://localhost:8061/purple/api/items/7{?projection}",
            "templated": true
          },
          "group": {
            "href": "http://localhost:8061/purple/api/items/7/group"
          }
        }
      },
      {
        "name": "Item One",
        "total": 319.99,
        "description": "Item one description",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/items/1"
          },
          "item": {
            "href": "http://localhost:8061/purple/api/items/1{?projection}",
            "templated": true
          },
          "group": {
            "href": "http://localhost:8061/purple/api/items/1/group"
          }
        }
      },
      {
        "name": "Item Four",
        "total": 118.86,
        "description": "Item four description",
        "_links": {
          "self": {
            "href": "http://localhost:8061/purple/api/items/4"
          },
          "item": {
            "href": "http://localhost:8061/purple/api/items/4{?projection}",
            "templated": true
          },
          "group": {
            "href": "http://localhost:8061/purple/api/items/4/group"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/api/items/search/findByGroupName?groupNameContains=gro&page=1&size=3&sort=name%2Cdesc&projection=totals"
    }
  }
}
~~~

# Create (POST)

    POST http://localhost:8061/purple/api/groups
    Content-Type: application/json
    
    {
        "name": "Group Six",
        "description": "Group six description"
    }

or

    POST http://localhost:8061/purple/api/items
    Content-Type:application/json
    
    {
        "group": "http://localhost:8061/purple/api/groups/4",
        "uuid": "9f84fa9a-4b9c-46f8-9098-4603efe7ccbc",
        "name": "Item Nine",
        "description": "Item nine description",
        "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
        "status": 3,
        "data": [
            {
                "value": 11.49
            },
            {
                "category": "http://localhost:8061/purple/categories/1",
                "value": 45.76
            }
        ]
    }
    
Note the resource URL in the JSON to specify the `group` and `category` parent entities.

Note that `config.setReturnBodyOnCreate(true);` is used to specify that a JSON representation
of the object (including the new `id`) is returned in the response body.

# Update (PUT)

PUT replaces the entire resource so all values must be specified.

    PUT http://localhost:8061/purple/api/groups/4
    Content-Type: application/json
    
    {
      "name": "Group Four updated",
      "description": "New group four description"
    }
    
or
    
    PUT http://localhost:8061/purple/api/items/4
    Content-Type:application/json
    
    {
        "group": "http://localhost:8061/purple/groups/4",
        "uuid": "8574a479-b583-4db4-9c03-bfd0ddc7a069",
        "name": "Item four name",
        "description": "New Item four description",
        "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
        "status": 3,
        "data": [
            {
                "category": "http://localhost:8061/purple/categories/3",
                "value": 11.49
            },
            {
                "value": 45.76
            }
        ]
    }
    
Note I couldn't alter the _number_ of items in the `data` array with this command
(ie adding or removing items). 
I suspect this is a JPA issue rather than a Spring Data REST issue.
Maybe use event handlers to fix this?


# Update (PATCH)

PATCH is similar to PUT but partially updates the resources state.

    PATCH http://localhost:8061/purple/api/groups/4
    Content-Type: application/json
    
    {
      "description": "Group four updated again"
    }
    
or

    PATCH http://localhost:8061/purple/api/items/8
    Content-Type: application/json
    
    {
      "group": "http://localhost:8061/purple/groups/4",
      "description": "Item eight description updated",
      "status": 6
    }

Note the change to the owning group.

# Delete (DELETE)

    DELETE http://localhost:8061/purple/api/groups/5

# Create Association

We have a many to many relationship between country and person.

~~~java
@Entity
@Table(name="person")
public class PersonEntity {

    @ManyToMany
    @JoinTable(
            name = "visitor",
            joinColumns = @JoinColumn(name = "psn_id"),
            inverseJoinColumns = @JoinColumn(name = "cty_id"))
    private List<CountryEntity> countries;
~~~

~~~java
@Entity
@Table(name = "country")
public class CountryEntity {

    @ManyToMany(mappedBy = "countries")
    private List<PersonEntity> people;
~~~

We can link countries to people like this

    POST http://localhost:8061/purple/api/people/2/countries
    Content-Type: text/uri-list
    
    http://localhost:8061/purple/countries/20
    http://localhost:8061/purple/countries/24
    http://localhost:8061/purple/countries/30

Note this works with other association types too (eg one to one, one to many etc)

# Events

The following events are available

* `BeforeCreateEvent`
* `AfterCreateEvent`
* `BeforeSaveEvent`
* `AfterSaveEvent`
* `BeforeLinkSaveEvent`
* `AfterLinkSaveEvent`
* `BeforeDeleteEvent`
* `AfterDeleteEvent`

Events are registered as follows.

~~~java
@Component
@RepositoryEventHandler(ItemEntity.class)
public class ItemEntityEventHandler {

    @HandleBeforeCreate
    public void handleBeforeCreate(ItemEntity itemEntity) {
        System.out.println(itemEntity);
    }

    @HandleAfterCreate
    public void handleAfterCreate(ItemEntity itemEntity) {
        System.out.println(itemEntity);
    }

    @HandleBeforeSave
    public void handleBeforeSave(ItemEntity itemEntity) {
        System.out.println(itemEntity);
    }

    @HandleAfterSave
    public void handleAfterSave(ItemEntity itemEntity) {
        System.out.println(itemEntity);
    }
}
~~~

# `@RepositoryRestController`

`@RepositoryRestController` is used to override _Spring Data REST managed resources_.
In fact, Spring makes sure the URI refers to an existing repository.

~~~java
@RepositoryRestController
public class CountryController {

    private final CountryRepository countryRepository;
    private final EntityLinks entityLinks;

    @Autowired
    public CountryController(final CountryRepository countryRepository, final EntityLinks entityLinks) {
        this.countryRepository = countryRepository;
        this.entityLinks = entityLinks;
    }

    @GetMapping("countries/{code}/visitors")
    public ResponseEntity<?> visitors(@PathVariable("code") final String code)  {

        final Optional<CountryEntity> country = countryRepository.findByCode(code);
        if (country.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        final List<Resource> visitors = country.get()
                .getPeople()
                .stream()
                .map(Resource::new)
                .collect(Collectors.toList());

        final Resources<Resource> resources = new Resources<>(visitors);
        resources.add(linkTo(methodOn(CountryController.class).visitors(null)).withSelfRel());
        resources.add(entityLinks.linkToSingleResource(CountryEntity.class, country.get().getId()).withRel("country"));
        return ResponseEntity.ok(resources);
    }
}
~~~

    http://localhost:8061/purple/api/countries/BB/visitors
    
~~~json
{
  "_embedded": {
    "people": [
      {
        "firstName": "Bob",
        "lastName": "Jones",
        "age": 31
      },
      {
        "firstName": "Sarah",
        "lastName": "Jones",
        "age": 27
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8061/purplecountries/{code}/visitors",
      "templated": true
    },
    "country": {
      "href": "http://localhost:8061/purple/api/countries/20"
    }
  }
}
~~~

# `@BasePathAwareController`

> If you are not interested in entity-specific operations but still want to build custom operations underneath `basePath`, 
such as Spring MVC views, resources, and others, use `@BasePathAwareController`.

~~~java
@BasePathAwareController
public class StatisticsController {

    private final CountryRepository countryRepository;
    private final PersonRepository personRepository;
    private final EntityLinks entityLinks;

    @Autowired
    public StatisticsController(final CountryRepository countryRepository,
                                final PersonRepository personRepository,
                                final EntityLinks entityLinks) {
        this.countryRepository = countryRepository;
        this.personRepository = personRepository;
        this.entityLinks = entityLinks;
    }

    @GetMapping("/stats")
    public ResponseEntity<?> stats()  {

        final int countryCount = countryRepository.findAll().size();
        final int userCount = personRepository.findAll().size();

        final StatisticsResource countryStatistics = new StatisticsResource("country", countryCount);
        final StatisticsResource personStatistics = new StatisticsResource("person", userCount);

        final Resources<Resource<StatisticsResource>> resources = new Resources<>(
                List.of(
                        new Resource<>(countryStatistics),
                        new Resource<>(personStatistics)
                )
        );
        resources.add(linkTo(methodOn(StatisticsController.class).stats()).withSelfRel());
        return ResponseEntity.ok(resources);
    }
}

~~~

This introduces `StatisticsResource`. We can name the resource like this

~~~java
@Relation(value = "statistics", collectionRelation = "statistics")
public class StatisticsResource {
~~~

    http://localhost:8061/purple/api/stats

~~~json
{
  "_embedded": {
    "statistics": [
      {
        "name": "country",
        "count": 245
      },
      {
        "name": "person",
        "count": 4
      }
    ]
  },
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/stats"
    }
  }
}
~~~

# `@RestController`

Use `@Controller` or `@RestController` for code that is totally outside the scope of Spring Data REST. 
This extends to request handling, message converters, exception handling, and other uses.

~~~java
@RestController
public class VersionController {

    @GetMapping("/version")
    public ResponseEntity<?> stats()  {
        final Resource<String> version = new Resource<>("1.2.3");
        version.add(linkTo(methodOn(VersionController.class).stats()).withSelfRel());
        return ResponseEntity.ok(version);
    }
}
~~~

    http://localhost:8061/purple/version
    
Note: no `/api`

~~~json
{
  "content": "1.2.3",
  "_links": {
    "self": {
      "href": "http://localhost:8061/purple/version"
    }
  }
}
~~~

---

# Access

## Detection

Use the `Annotation` detection strategy.

~~~java
@Component
public class RestApiConfiguration implements RepositoryRestConfigurer {

    @Override
    public void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.setRepositoryDetectionStrategy(RepositoryDetectionStrategy.RepositoryDetectionStrategies.ANNOTATED);
        config.setReturnBodyOnCreate(true);
    }

}
~~~

This requires the `@RepositoryRestResource` annotation on the repository.

~~~java
@RepositoryRestResource (
        path = "people",
        collectionResourceRel = "people",
        collectionResourceDescription = @Description("A collection of people"),
        itemResourceRel = "person",
        itemResourceDescription = @Description("A single person"),
        excerptProjection = PersonNameProjection.class
)
public interface PersonRepository extends JpaRepository<PersonEntity, Long> {
~~~

## Repository Methods

Only include the methods to be exposed.
Extending `CrudRepository` or `JpaRepository` allows POST, PUT, DELETE etc

`CategoryRepository` extends `Repository` so only the methods defined on this class are exposed.

~~~java
@RepositoryRestResource (
        path = "categories",
        collectionResourceRel = "categories",
        collectionResourceDescription = @Description("A collection of categories"),
        itemResourceRel = "category",
        itemResourceDescription = @Description("A single category")
)
public interface CategoryRepository extends Repository<CategoryEntity, Long> {

    Optional<CategoryEntity> findById(final Long id);

    List<CategoryEntity> findAll();

    Optional<CategoryEntity> findByName(final String name);

}
~~~

## `@RestResource`

Alternatively, use `@RestResource(exported = false)` to exclude the method.

~~~java
@RepositoryRestResource (
        path = "countries",
        collectionResourceRel = "countries",
        collectionResourceDescription = @Description("A collection of countries"),
        itemResourceRel = "country",
        itemResourceDescription = @Description("A single country")
)
public interface CountryRepository extends JpaRepository<CountryEntity, Long> {

    @RestResource(path = "findByName")
    List<CountryEntity> findByNameContainingIgnoreCase(final String nameContains);

    @RestResource(exported = false)
    Optional<CountryEntity> findByCode(final String code);

    @RestResource(exported = false)
    @Override
    void deleteById(Long id);

    @RestResource(exported = false)
    @Override
    void delete(CountryEntity countryEntity);

    @RestResource(exported = false)
    @Override
    void deleteAll(Iterable<? extends CountryEntity> var1);

}
~~~

Note that `@RestResource(exported = false)` can be used on a repository class, a repository method
or an entity method.

~~~java

@Entity
@Table(name="item")
public class ItemEntity {
    
    @RestResource(exported = false)
    public UUID getCorrelationId() {
        return correlationId;
    }
~~~

## Spring Security

Use Spring Security to protect endpoints.

~~~java
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Override
    void deleteById(Long id);
~~~

---

# HAL Browser

http://localhost:8061/purple/api

# ALPS

    http://localhost:8061/purple/api/profile/people

~~~json
{
  "alps": {
    "version": "1.0",
    "descriptor": [
      {
        "id": "person-representation",
        "href": "http://localhost:8061/purple/api/profile/people",
        "doc": {
          "format": "TEXT",
          "value": "A single person"
        },
        "descriptor": [
          {
            "name": "firstName",
            "type": "SEMANTIC"
          },
          {
            "name": "lastName",
            "type": "SEMANTIC"
          },
          {
            "name": "age",
            "type": "SEMANTIC"
          },
          {
            "name": "countries",
            "type": "SAFE",
            "rt": "http://localhost:8061/purple/api/profile/countries#country-representation"
          }
        ]
      },
      {
        "id": "get-people",
        "name": "people",
        "type": "SAFE",
        "doc": {
          "format": "TEXT",
          "value": "A collection of people"
        },
        "descriptor": [
          {
            "name": "page",
            "type": "SEMANTIC",
            "doc": {
              "format": "TEXT",
              "value": "The page to return."
            }
          },
          {
            "name": "size",
            "type": "SEMANTIC",
            "doc": {
              "format": "TEXT",
              "value": "The size of the page to return."
            }
          },
          {
            "name": "sort",
            "type": "SEMANTIC",
            "doc": {
              "format": "TEXT",
              "value": "The sorting criteria to use to calculate the content of the page."
            }
          },
          {
            "name": "projection",
            "type": "SEMANTIC",
            "doc": {
              "format": "TEXT",
              "value": "The projection that shall be applied when rendering the response. Acceptable values available in nested descriptors."
            },
            "descriptor": [
              {
                "name": "name",
                "type": "SEMANTIC",
                "descriptor": [
                  {
                    "name": "fullName",
                    "type": "SEMANTIC"
                  }
                ]
              },
              {
                "name": "personNameProjection",
                "type": "SEMANTIC",
                "descriptor": [
                  {
                    "name": "fullName",
                    "type": "SEMANTIC"
                  }
                ]
              }
            ]
          }
        ],
        "rt": "#person-representation"
      },
      {
        "id": "create-people",
        "name": "people",
        "type": "UNSAFE",
        "doc": {
          "format": "TEXT",
          "value": "A collection of people"
        },
        "rt": "#person-representation"
      },
      {
        "id": "delete-person",
        "name": "person",
        "type": "IDEMPOTENT",
        "doc": {
          "format": "TEXT",
          "value": "A single person"
        },
        "rt": "#person-representation"
      },
      {
        "id": "get-person",
        "name": "person",
        "type": "SAFE",
        "doc": {
          "format": "TEXT",
          "value": "A single person"
        },
        "descriptor": [
          {
            "name": "projection",
            "type": "SEMANTIC",
            "doc": {
              "format": "TEXT",
              "value": "The projection that shall be applied when rendering the response. Acceptable values available in nested descriptors."
            },
            "descriptor": [
              {
                "name": "name",
                "type": "SEMANTIC",
                "descriptor": [
                  {
                    "name": "fullName",
                    "type": "SEMANTIC"
                  }
                ]
              },
              {
                "name": "personNameProjection",
                "type": "SEMANTIC",
                "descriptor": [
                  {
                    "name": "fullName",
                    "type": "SEMANTIC"
                  }
                ]
              }
            ]
          }
        ],
        "rt": "#person-representation"
      },
      {
        "id": "update-person",
        "name": "person",
        "type": "IDEMPOTENT",
        "doc": {
          "format": "TEXT",
          "value": "A single person"
        },
        "rt": "#person-representation"
      },
      {
        "id": "patch-person",
        "name": "person",
        "type": "UNSAFE",
        "doc": {
          "format": "TEXT",
          "value": "A single person"
        },
        "rt": "#person-representation"
      },
      {
        "name": "findByLastNameContainingIgnoreCase",
        "type": "SAFE",
        "descriptor": [
          {
            "name": "nameContains",
            "type": "SEMANTIC"
          }
        ]
      }
    ]
  }
}
~~~

Note the descriptions are picked up from `@RepositoryRestResource`.

~~~java
@RepositoryRestResource (
        path = "people",
        collectionResourceRel = "people",
        collectionResourceDescription = @Description("A collection of people"),
        itemResourceRel = "person",
        itemResourceDescription = @Description("A single person"),
        excerptProjection = PersonNameProjection.class
)
~~~

Descriptions can be picked up from `rest-messages.properties` too.

~~~properties
rest.description.countryEntity=Lots of countries
rest.description.countryEntity.code=The country code
rest.description.countryEntity.name=The name of the country
~~~


# JSON Schema

Use `Accept:application/schema+json` to get JSON Schema format

    http://localhost:8061/purple/api/profile/people

~~~json
{
  "title": "Person entity",
  "properties": {
    "firstName": {
      "title": "First name",
      "readOnly": false,
      "type": "string"
    },
    "lastName": {
      "title": "Last name",
      "readOnly": false,
      "type": "string"
    },
    "countries": {
      "title": "Countries",
      "readOnly": false,
      "type": "string",
      "format": "uri"
    },
    "age": {
      "title": "Age",
      "readOnly": false,
      "type": "integer"
    }
  },
  "definitions": {},
  "type": "object",
  "$schema": "http://json-schema.org/draft-04/schema#"
}
~~~
