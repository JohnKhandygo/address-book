Generated using [Spring initializr](https://start.spring.io/#!type=gradle-project-kotlin&language=kotlin&platformVersion=3.1.2&packaging=jar&jvmVersion=17&groupId=sandbox&artifactId=address-book&name=address-book&description=&packageName=sandbox.addressbook&dependencies=web,data-rest,data-mongodb,actuator,testcontainers)

This is a Spring Boot application with "business" logic.

## How to run it locally

1. Run `./gradlew localRun`.
2. Verify it is up with `http :8080/actuator/health`.
3. Access API with `http :8080`.
