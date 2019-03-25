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
@RepositoryRestResource(path = "groups", collectionResourceRel = "groups")
public interface GroupRepository extends CrudRepository<GroupEntity, Long> {

    Optional<GroupEntity> findByName(final String name);

}
~~~

~~~java
@RepositoryRestResource(path = "items", collectionResourceRel = "items")
public interface ItemRepository extends CrudRepository<ItemEntity, Long> {

    Optional<ItemEntity> findByUuid(final UUID id);

    List<ItemEntity> findByCorrelationId(final UUID correlationId);

    @RestResource(path = "findByGroupName")
    List<ItemEntity> findByGroupNameContainingIgnoreCase(final String groupNameContains, final Pageable pageable);
}
~~~

~~~java
@RepositoryRestResource(path = "categories", collectionResourceRel = "categories", excerptProjection = CategoryNameProjection.class)
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(final String name);

}
~~~

~~~java
@RepositoryRestResource(path = "countries", collectionResourceRel = "countries")
public interface CountryRepository extends PagingAndSortingRepository<CountryEntity, Long> {

    @RestResource(path = "findByName")
    List<CountryEntity> findByNameContainingIgnoreCase(final String nameContains);
}
~~~

# Resource Discoverability

    http://localhost:8061/purple

~~~json
{
    "_links": {
        "items": {
            "href": "http://localhost:8061/purple/items{?projection}",
            "templated": true
        },
        "categories": {
            "href": "http://localhost:8061/purple/categories{?projection}",
            "templated": true
        },
        "groups": {
            "href": "http://localhost:8061/purple/groups"
        },
        "countries": {
            "href": "http://localhost:8061/purple/countries{?page,size,sort}",
            "templated": true
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
                    "groupEntity": {
                        "href": "http://localhost:8061/purple/groups/1"
                    },
                    "items": {
                        "href": "http://localhost:8061/purple/groups/1/items{?projection}",
                        "templated": true
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
                    "groupEntity": {
                        "href": "http://localhost:8061/purple/groups/2"
                    },
                    "items": {
                        "href": "http://localhost:8061/purple/groups/2/items{?projection}",
                        "templated": true
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
        "groupEntity": {
            "href": "http://localhost:8061/purple/groups/3"
        },
        "items": {
            "href": "http://localhost:8061/purple/groups/3/items{?projection}",
            "templated": true
        }
    }
}
~~~

# Object Graph

Note that the result above is limited to the `groups` resource only.<br/>
However, the `items` resource includes `data` resource as well.

    http://localhost:8061/purple/items/7

~~~json
{
    "uuid": "fb8d5122-dfcd-4509-a99e-222e862a1658",
    "name": "Item Seven",
    "description": "Item seven description",
    "correlationId": "e4b4a967-3758-4479-9a26-7ed5608f978a",
    "status": 3,
    "createdAt": "2019-03-23T13:44:02.729391",
    "data": [
        {
            "value": 32.45,
            "createdAt": "2019-03-23T13:44:03.436905",
            "_embedded": {
                "category": {
                    "details": "Category Three - Category three description",
                    "_links": {
                        "self": {
                            "href": "http://localhost:8061/purple/categories/3{?projection}",
                            "templated": true
                        }
                    }
                }
            },
            "_links": {
                "item": {
                    "href": "http://localhost:8061/purple/items/7{?projection}",
                    "templated": true
                },
                "category": {
                    "href": "http://localhost:8061/purple/categories/3{?projection}",
                    "templated": true
                }
            }
        }
    ],
    "total": 32.45,
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/items/7"
        },
        "itemEntity": {
            "href": "http://localhost:8061/purple/items/7{?projection}",
            "templated": true
        },
        "group": {
            "href": "http://localhost:8061/purple/items/7/group"
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
    "uuid": "8574a479-b583-4db4-9c03-bfd0ddc7a069",
    "name": "Item four name",
    "description": "New Item four description",
    "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
    "status": 3,
    "createdAt": "2019-03-23T13:44:02.627358",
    "data": [
        {
            "value": 11.49,
            "createdAt": "2019-03-23T13:44:03.260411",
            "_embedded": {
                "category": {
                    "details": "Category Three - Category three description",
                    "_links": {
                        "self": {
                            "href": "http://localhost:8061/purple/categories/3{?projection}",
                            "templated": true
                        }
                    }
                }
            },
            "_links": {
                "item": {
                    "href": "http://localhost:8061/purple/items/4{?projection}",
                    "templated": true
                },
                "category": {
                    "href": "http://localhost:8061/purple/categories/3{?projection}",
                    "templated": true
                }
            }
        },
        {
            "value": 45.76,
            "createdAt": "2019-03-23T13:44:03.289638",
            "_links": {
                "item": {
                    "href": "http://localhost:8061/purple/items/4{?projection}",
                    "templated": true
                }
            }
        }
    ],
    "total": 57.25,
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/items/4"
        },
        "itemEntity": {
            "href": "http://localhost:8061/purple/items/4{?projection}",
            "templated": true
        },
        "group": {
            "href": "http://localhost:8061/purple/items/4/group"
        }
    }
}
~~~
    
# Projections

Projections alter the view of the returned data.

### Subset

This projection returns the item name and description

~~~java
@Projection(name = "name", types = { Item.class })
public interface ItemNameProjection {
    String getName();
    String getDescription();
}
~~~

    http://localhost:8061/purple/items/1?projection=name
    
returns a subset of the `item` resource

~~~json
{
    "name": "Item One",
    "description": "Item one description",
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/items/1"
        },
        "item": {
            "href": "http://localhost:8061/purple/items/1{?projection}",
            "templated": true
        },
        "group": {
            "href": "http://localhost:8061/purple/items/1/group"
        }
    }
}
~~~

### Using SpEL

~~~java
@Projection(name = "name", types = { Category.class })
public interface CategoryNameProjection {

    @Value("#{target.name} - #{target.description}")
    String getDetails();
}
~~~

    http://localhost:8061/purple/categories/1?projection=name
    
~~~json
{
    "name": "Item One",
    "description": "Item one description",
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/items/1"
        },
        "itemEntity": {
            "href": "http://localhost:8061/purple/items/1{?projection}",
            "templated": true
        },
        "group": {
            "href": "http://localhost:8061/purple/items/1/group"
        }
    }
}
~~~

### Superset

~~~java
@Projection(name = "full", types = { Item.class })
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
@Projection(name = "full", types = { Data.class })
public interface DataFullProjection {
    String getId();
    BigDecimal getValue();
    LocalDateTime getCreatedAt();
    CategoryNameProjection getCategory();
}
~~~

    http://localhost:8061/purple/items/4?projection=full

~~~json
{
    "name": "Item four name",
    "id": "4",
    "status": 3,
    "data": [
        {
            "value": 11.49,
            "id": "15",
            "createdAt": "2019-03-23T13:44:03.260411",
            "category": {
                "details": "Category Three - Category three description",
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/categories/3{?projection}",
                        "templated": true
                    }
                }
            }
        },
        {
            "value": 45.76,
            "id": "16",
            "createdAt": "2019-03-23T13:44:03.289638",
            "category": null
        }
    ],
    "uuid": "8574a479-b583-4db4-9c03-bfd0ddc7a069",
    "correlationId": "128a7512-0b92-4f49-8f61-15dabbd757b8",
    "createdAt": "2019-03-23T13:44:02.627358",
    "description": "New Item four description",
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/items/4"
        },
        "item": {
            "href": "http://localhost:8061/purple/items/4{?projection}",
            "templated": true
        },
        "group": {
            "href": "http://localhost:8061/purple/items/4/group"
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
@Projection(name = "name", types = { CategoryEntity.class })
public interface CategoryNameProjection {

    @Value("#{target.name} - #{target.description}")
    String getDetails();
}
~~~

~~~java
@RepositoryRestResource(path = "categories", collectionResourceRel = "categories", excerptProjection = CategoryNameProjection.class)
public interface CategoryRepository extends CrudRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(final String name);

}
~~~

    http://localhost:8061/purple/categories
    
Projection is automatically applied

~~~json
{
    "_embedded": {
        "categories": [
            {
                "details": "Category One - Category one description",
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/categories/1"
                    },
                    "categoryEntity": {
                        "href": "http://localhost:8061/purple/categories/1{?projection}",
                        "templated": true
                    }
                }
            },
            {
                "details": "Category Two - Category two description",
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/categories/2"
                    },
                    "categoryEntity": {
                        "href": "http://localhost:8061/purple/categories/2{?projection}",
                        "templated": true
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/categories"
        },
        "profile": {
            "href": "http://localhost:8061/purple/profile/categories"
        },
        "search": {
            "href": "http://localhost:8061/purple/categories/search"
        }
    }
}
~~~

# Search

The `ItemRepository` looks like this.

~~~java
@RepositoryRestResource(excerptProjection = ItemNameProjection.class)
public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> findByUuid(final UUID id);

    List<Item> findByCorrelationId(final UUID correlationId);

    @RestResource(path = "findByGroupName")
    List<Item> findByGroupNameContainingIgnoreCase(final String groupNameContains, final Pageable pageable);
}
~~~

When we use the `/search` path

    http://localhost:8061/purple/items/search
    
we get a list of the query methods on the resource repository.

~~~json
{
    "_links": {
        "findByUuid": {
            "href": "http://localhost:8061/purple/items/search/findByUuid{?id,projection}",
            "templated": true
        },
        "findByCorrelationId": {
            "href": "http://localhost:8061/purple/items/search/findByCorrelationId{?correlationId,projection}",
            "templated": true
        },
        "findByGroupNameContainingIgnoreCase": {
            "href": "http://localhost:8061/purple/items/search/findByGroupName{?groupNameContains,page,size,sort,projection}",
            "templated": true
        },
        "self": {
            "href": "http://localhost:8061/purple/items/search"
        }
    }
}
~~~

    http://localhost:8061/purple/items/search/findByGroupName?groupNameContains=three
    
~~~json
{
    "_embedded": {
        "items": [
            {
                "name": "Item Seven",
                "description": "Item seven description",
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/items/7"
                    },
                    "item": {
                        "href": "http://localhost:8061/purple/items/7{?projection}",
                        "templated": true
                    },
                    "group": {
                        "href": "http://localhost:8061/purple/items/7/group"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/items/search/findByGroupName?groupName=three"
        }
    }
}
~~~

# Paging and Sorting

~~~java
@RepositoryRestResource
public interface CountryRepository extends PagingAndSortingRepository<Country, Long> {

    @RestResource(path = "findByName")
    List<Country> findByNameContainingIgnoreCase(final String nameContains);
}

~~~

    http://localhost:8061/purple/countries?page=5&size=3&sort=code,desc
    
~~~json
{
    "_embedded": {
        "countries": [
            {
                "code": "US",
                "name": "United States",
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/countries/233"
                    },
                    "country": {
                        "href": "http://localhost:8061/purple/countries/233"
                    }
                }
            },
            {
                "code": "UM",
                "name": "United States Minor Outlying Islands",
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/countries/234"
                    },
                    "country": {
                        "href": "http://localhost:8061/purple/countries/234"
                    }
                }
            },
            {
                "code": "UG",
                "name": "Uganda",
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/countries/229"
                    },
                    "country": {
                        "href": "http://localhost:8061/purple/countries/229"
                    }
                }
            }
        ]
    },
    "_links": {
        "first": {
            "href": "http://localhost:8061/purple/countries?page=0&size=3&sort=code,desc"
        },
        "prev": {
            "href": "http://localhost:8061/purple/countries?page=4&size=3&sort=code,desc"
        },
        "self": {
            "href": "http://localhost:8061/purple/countries"
        },
        "next": {
            "href": "http://localhost:8061/purple/countries?page=6&size=3&sort=code,desc"
        },
        "last": {
            "href": "http://localhost:8061/purple/countries?page=81&size=3&sort=code,desc"
        },
        "profile": {
            "href": "http://localhost:8061/purple/profile/countries"
        },
        "search": {
            "href": "http://localhost:8061/purple/countries/search"
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
@RepositoryRestResource(excerptProjection = ItemNameProjection.class)
public interface ItemRepository extends CrudRepository<Item, Long> {

    Optional<Item> findByUuid(final UUID id);

    List<Item> findByCorrelationId(final UUID correlationId);

    @RestResource(path = "findByGroupName")
    List<Item> findByGroupNameContainingIgnoreCase(final String groupName, final Pageable pageable);
}
~~~


And putting it all together

    http://localhost:8061/purple/items/search/findByGroupName?groupNameContains=gro&page=1&size=3&sort=name,desc&projection=totals

~~~json
{
    "_embedded": {
        "items": [
            {
                "name": "Item Seven",
                "description": "Item seven description",
                "total": 32.45,
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/items/7"
                    },
                    "item": {
                        "href": "http://localhost:8061/purple/items/7{?projection}",
                        "templated": true
                    },
                    "group": {
                        "href": "http://localhost:8061/purple/items/7/group"
                    }
                }
            },
            {
                "name": "Item One",
                "description": "Item one description",
                "total": 319.99,
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/items/1"
                    },
                    "item": {
                        "href": "http://localhost:8061/purple/items/1{?projection}",
                        "templated": true
                    },
                    "group": {
                        "href": "http://localhost:8061/purple/items/1/group"
                    }
                }
            },
            {
                "name": "Item Nine",
                "description": "Item nine description",
                "total": 57.25,
                "_links": {
                    "self": {
                        "href": "http://localhost:8061/purple/items/10"
                    },
                    "item": {
                        "href": "http://localhost:8061/purple/items/10{?projection}",
                        "templated": true
                    },
                    "group": {
                        "href": "http://localhost:8061/purple/items/10/group"
                    }
                }
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8061/purple/items/search/findByGroupName?groupName=gro&page=1&size=3&sort=name,desc&projection=totals"
        }
    }
}
~~~

# Create (POST)

    URL: http://localhost:8061/purple/groups
    Method: POST
    Content-Type: application/json
    Body:
    {
        "name": "Group Six",
        "description": "Group six description"
    }

or

    URL: http://localhost:8061/purple/items
    Type: POST
    Content-Type:application/json
    body: 
    {
        "group": "http://localhost:8061/purple/groups/4",
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

# Update (PUT)

PUT replaces the entire resource so all values must be specified.

    URL: http://localhost:8061/purple/groups/4
    Type: PUT
    Content-Type: application/json
    body: 
    {
        "name": "Group Four updated",
        "description": "New group four description"
    }
    
or
    
    URL: http://localhost:8061/purple/items/4
    Type: PUT
    Content-Type:application/json
    body: 
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


# Update (PATCH)

PATCH is similar to PUT but partially updates the resources state.

    URL: http://localhost:8061/purple/groups/4
    Type: PATCH
    Content-Type: application/json
    body: 
    {
        "description": "Group four updated again"
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
@Table(name="country")
public class CountryEntity {

    @ManyToMany(mappedBy = "countries")
    private List<PersonEntity> people;
~~~

We can link countries to people like this

    URL: http://localhost:8061/purple/people/1/countries
    Content-Type: text/uri-list
    body:
    http://localhost:8061/purple/countries/20
    http://localhost:8061/purple/countries/24
    http://localhost:8061/purple/countries/30

Note this works with other association types too (eg one to one, one to many etc)

---

# HAL Browser

http://localhost:8061/purple
