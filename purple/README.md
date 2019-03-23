# Purple

## Features

* Spring Data REST
* HATEOAS

---

# Schema

<img src="database-schema.png" width="600" height="600"/>

---

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

## Object Graph

Note that the result above is limited to the `groups` resource only. 
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

# Association Resource

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
