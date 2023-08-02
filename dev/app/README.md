Generated using [Spring initializr](https://start.spring.io/#!type=gradle-project-kotlin&language=kotlin&platformVersion=3.1.2&packaging=jar&jvmVersion=17&groupId=sandbox&artifactId=address-book&name=address-book&description=&packageName=sandbox.addressbook&dependencies=web,data-rest,data-mongodb,actuator,testcontainers)

This is a Spring Boot application with "business" logic.

## How to run it locally

1. Run `./gradlew localRun`.
2. Verify it is up with `http :8080/actuator/health`.
3. Access API with `http :8080`.

## Example API calls

### Save new address

```
evgenii@EPNLHOOW0091:~$ http post :8080/addresses userID='123' name='PostNL Office' postalCode='2321 BT' houseNumber='176-178' -v
POST /addresses HTTP/1.1
Accept: application/json, */*;q=0.5
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 93
Content-Type: application/json
Host: localhost:8080
User-Agent: HTTPie/2.6.0

{
    "houseNumber": "176-178",
    "name": "PostNL Office",
    "postalCode": "2321 BT",
    "userID": "123"
}


HTTP/1.1 201
Connection: keep-alive
Content-Type: application/json
Date: Wed, 02 Aug 2023 13:43:34 GMT
Keep-Alive: timeout=60
Location: http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8
Transfer-Encoding: chunked
Vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers

{
    "_links": {
        "address": {
            "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
        },
        "self": {
            "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
        }
    },
    "addition": null,
    "houseNumber": "176-178",
    "name": "PostNL Office",
    "postalCode": "2321 BT",
    "userID": "123"
}
```
### Read addresses

```
evgenii@EPNLHOOW0091:~$  http get :8080/addresses -v
GET /addresses HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/2.6.0



HTTP/1.1 200
Connection: keep-alive
Content-Type: application/hal+json
Date: Wed, 02 Aug 2023 13:46:02 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers

{
    "_embedded": {
        "addresses": [
            {
                "_links": {
                    "address": {
                        "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
                    },
                    "self": {
                        "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
                    }
                },
                "addition": null,
                "houseNumber": "176-178",
                "name": "PostNL Office",
                "postalCode": "2321 BT",
                "userID": "123"
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8080/profile/addresses"
        },
        "search": {
            "href": "http://localhost:8080/addresses/search"
        },
        "self": {
            "href": "http://localhost:8080/addresses?page=0&size=20"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}
```

### Read address by ID 

```
evgenii@EPNLHOOW0091:~$ http get :8080/addresses/64ca5d861ad82d04ae1510d8 -v
GET /addresses/64ca5d861ad82d04ae1510d8 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/2.6.0



HTTP/1.1 200
Connection: keep-alive
Content-Type: application/hal+json
Date: Wed, 02 Aug 2023 13:47:57 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers

{
    "_links": {
        "address": {
            "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
        },
        "self": {
            "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
        }
    },
    "addition": null,
    "houseNumber": "176-178",
    "name": "PostNL Office",
    "postalCode": "2321 BT",
    "userID": "123"
}
```
### Find address by name

```
evgenii@EPNLHOOW0091:~$ http get :8080/addresses/search/findByName name=='PostNL Office' -v
GET /addresses/search/findByName?name=PostNL+Office HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Host: localhost:8080
User-Agent: HTTPie/2.6.0



HTTP/1.1 200
Connection: keep-alive
Content-Type: application/hal+json
Date: Wed, 02 Aug 2023 13:49:35 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers

{
    "_embedded": {
        "addresses": [
            {
                "_links": {
                    "address": {
                        "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
                    },
                    "self": {
                        "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
                    }
                },
                "addition": null,
                "houseNumber": "176-178",
                "name": "PostNL Office",
                "postalCode": "2321 BT",
                "userID": "123"
            }
        ]
    },
    "_links": {
        "self": {
            "href": "http://localhost:8080/addresses/search/findByName?name=PostNL+Office"
        }
    }
}
```

### Delete an address by ID

```
evgenii@EPNLHOOW0091:~$ http delete :8080/addresses/64ca5d861ad82d04ae1510d8 -v
DELETE /addresses/64ca5d861ad82d04ae1510d8 HTTP/1.1
Accept: */*
Accept-Encoding: gzip, deflate
Connection: keep-alive
Content-Length: 0
Host: localhost:8080
User-Agent: HTTPie/2.6.0



HTTP/1.1 200
Connection: keep-alive
Content-Type: application/hal+json
Date: Wed, 02 Aug 2023 13:49:56 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin, Access-Control-Request-Method, Access-Control-Request-Headers

{
    "_links": {
        "address": {
            "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
        },
        "self": {
            "href": "http://localhost:8080/addresses/64ca5d861ad82d04ae1510d8"
        }
    },
    "addition": null,
    "houseNumber": "176-178",
    "name": "PostNL Office",
    "postalCode": "2321 BT",
    "userID": "123"
}
```
