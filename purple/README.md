# Purple

## Features

* Spring Data REST
* HATEOAS

---

# Schema

<img src="database-schema.png" width="700" height="700"/>

---

# HATEOAS

## Resource Discoverability

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

## Collection Resources

    http://localhost:8061/purple/groups

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
                },
                ...
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

## Item Resources

    http://localhost:8061/purple/groups/3
    
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

## Object Graph

Note that the result above is limited to the `group` resource only.

> Spring Data REST tries very hard to render your object graph correctly. It tries to serialize 
unmanaged beans as normal POJOs, and it tries to create links to managed beans where necessary.
