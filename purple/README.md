# Purple

## Features

* Spring Data REST
* HATEOAS

---

# Schema

<img src="database-schema.png"/>

---

# HATEOAS

## Resource Discoverability

    http://localhost:8061/purple
    
produces
   
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

## Collection Resources

    http://localhost:8061/purple/groups
    
produces

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

    http://localhost:8061/purple/groups/1
    
produces

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
    }
