# Purple

## Features

* Spring Data REST
* HATEOAS

---

# Schema

<img src="database-schema.png" width="600" height="600"/>

---

# Repositories

~~~java
@RepositoryRestResource
public interface GroupRepository extends CrudRepository<Group, Long> {

    Optional<Group> findByName(final String name);

}
~~~

~~~java
@RepositoryRestResource
public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> findByUuid(final String uuid);

    List<Item> findByCorrelationId(final String correlationId, final Pageable pageable);

    List<Item> findByGroupName(final String name, final Pageable pageable);
}
~~~

~~~java
@RepositoryRestResource
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {
}
~~~


# Resource Discoverability

    http://localhost:8061/purple

~~~json
{
    "_links": {
        "items": {
            "href": "http://localhost:8061/purple/items"
        },
        "groups": {
            "href": "http://localhost:8061/purple/groups"
        },
        "countries": {
            "href": "http://localhost:8061/purple/countries"
        },
        "profile": {
            "href": "http://localhost:8061/purple/profile"
        }
    }
}
~~~

# Collection Resources

    http://localhost:8061/purple/groups

~~~json
{
    "_embedded": {
        "groups": [
            {
                "name": "Group One",
                "description": "Group one description",
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/groups/1"
                    },
                    "group": {
                        "href": "http://localhost:8061/purple/groups/1"
                    },
                    "items": {
                        "href": "http://localhost:8061/purple/groups/1/items"
                    }
                }
            },
            {
                "name": "Group Two",
                "description": "Group two description",
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/groups/2"
                    },
                    "group": {
                        "href": "http://localhost:8061/purple/groups/2"
                    },
                    "items": {
                        "href": "http://localhost:8061/purple/groups/2/items"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/groups"
        },
        "profile": {
            "href": "http://localhost:8061/purple/profile/groups"
        },
        "search": {
            "href": "http://localhost:8061/purple/groups/search"
        }
    }
}
~~~

# Item Resources

    http://localhost:8061/purple/groups/3

~~~json    
{
    "name": "Group Three",
    "description": "Group three description",
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/groups/3"
        },
        "group": {
            "href": "http://localhost:8061/purple/groups/3"
        },
        "items": {
            "href": "http://localhost:8061/purple/groups/3/items"
        }
    }
}
~~~

# Object Graph

Note that the result above is limited to the `groups` resource only.<br/>
However, the `items` resource includes `data` and `category`.

    http://localhost:8061/purple/items/3

~~~json
{
    "uuid": "85bf245e-a941-4a18-8d4b-15c3d0aebd23",
    "name": "Item Three",
    "description": "Item three description",
    "correlationId": "e4b4a967-3758-4479-9a26-7ed5608f978a",
    "status": 1,
    "createdAt": "2019-03-20T19:45:37.444026",
    "data": [
        {
            "value": 19.98,
            "createdAt": "2019-03-20T19:45:38.199292",
            "category": {
                "name": "Category Six",
                "description": "Category six description"
            },
            "_links": {
                "item": {
                    "href": "http://localhost:8061/purple/items/3"
                }
            }
        },
        {
            "value": 9.86,
            "createdAt": "2019-03-20T19:45:38.236705",
            "category": {
                "name": "Category One",
                "description": "Category one description"
            },
            "_links": {
                "item": {
                    "href": "http://localhost:8061/purple/items/3"
                }
            }
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/items/3"
        },
        "item": {
            "href": "http://localhost:8061/purple/items/3"
        },
        "group": {
            "href": "http://localhost:8061/purple/items/3/group"
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

    http://localhost:8061/purple/groups/2/items/4
    
~~~json
{
    "uuid": "4b30d7c8-2f17-49da-bff9-3a04364c5a08",
    "name": "Item Four",
    "description": "Item four description",
    "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
    "status": 3,
    "createdAt": "2019-03-20T19:45:37.486368",
    "data": [
        {
            "value": 111.43,
            "createdAt": "2019-03-20T19:45:38.337754",
            "category": {
                "name": "Category Three",
                "description": "Category three description"
            },
            "_links": {
                "item": {
                    "href": "http://localhost:8061/purple/items/4"
                }
            }
        },
        {
            "value": 7.43,
            "createdAt": "2019-03-20T19:45:38.37919",
            "category": {
                "name": "Category Four",
                "description": "Category four description"
            },
            "_links": {
                "item": {
                    "href": "http://localhost:8061/purple/items/4"
                }
            }
        }
    ],
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/items/4"
        },
        "item": {
            "href": "http://localhost:8061/purple/items/4"
        },
        "group": {
            "href": "http://localhost:8061/purple/items/4/group"
        }
    }
}
~~~

# Create (POST)

    URL: http://localhost:8061/purple/groups
    Method: POST
    Content-Type:application/json
    Body:
    {
        "name": "Group Six",
        "description": "Group six description"
    }

Use the resource URL to specify a parent entity.

    URL: http://localhost:8061/purple/items
    Type: POST
    Content-Type:application/json
    body: 
    {
        "group": "http://localhost:8061/purple/groups/8",
        "uuid": "9f84fa9a-4b9c-46f8-9098-4603efe7ccbc",
        "name": "Item Nine",
        "description": "Item nine description",
        "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
        "status": 3
    }

# Update (PUT)

PUT replaces the entire resource so all values mist be specified.

    URL: http://localhost:8061/purple/groups/4
    Type: PUT
    Content-Type:application/json
    body: 
    {
        "name": "Group Five updated",
        "description": "New group five description"
    }
    
or
    
    URL: http://localhost:8061/purple/items/8
    Type: PUT
    Content-Type:application/json
    body: 
    {
        "group": "http://localhost:8061/purple/groups/4",
        "uuid": "8574a479-b583-4db4-9c03-bfd0ddc7a069",
        "name": "New Item one",
        "description": "New Item one description",
        "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
        "status": 3,
        "data": [
            {
                "value": 11.49
            },
            {
                "value": 45.76
            }
        ]
    }
    
Note the creation of the `data` sub-resource.


# Update (PATCH)

PATCH is similar to PUT but partially updates the resources state.

    URL: http://localhost:8061/purple/groups/4
    Type: PATCH
    Content-Type:application/json
    body: 
    {
        "description": "Group four updated"
    }
    
or

    URL: http://localhost:8061/purple/items/8
    Type: PATCH
    Content-Type:application/json
    body: 
    {
        "group": "http://localhost:8061/purple/groups/3",
        "description": "Item eight description updated",
        "status": 6
    }

Note the change to the owning group.

# Delete (DELETE)


    URL: http://localhost:8061/purple/groups/4
    Type: DELETE
