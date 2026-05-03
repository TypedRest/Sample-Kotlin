# TypedRest Address Book Sample

This is a sample project for using [TypedRest for Java](https://github.com/TypedRest/TypedRest-Java) with Kotlin. It provides a simple REST API for storing contacts in an address book and uses SQLite for persistence.

The code is split into:

- [service/](service/): a [Spring Boot](https://spring.io/projects/spring-boot) Service
- [client/](client/): a TypedRest client library
- [dto/](dto/): DTOs shared by the Client and the Service

## Building

Requires JDK 21 or later. Build everything with:

```sh
./gradlew build
```

Run the service:

```sh
./gradlew :service:bootRun
```

The OpenAPI/Swagger UI is then available at <http://localhost:8080/swagger-ui.html>.
