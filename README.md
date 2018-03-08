# Spring Demo

This application is intended to demonstrate some common pieces that are frequently used by Spring application at Uken Games. Some of these pieces include:
- Our common parent POM including:
  - Datadog APM and custom metrics
  - Common Spring / Spring boot starters
  - a code formatter
- MySQL database adapter, including Flyway DB for running migrations
- Interaction with Redis
- The Thymeleaf templating engine & how to serve simple web pages 

## Database Migrations

Database migration are managed through FlywayDB, which has a maven plugin (installed here via the pom.xml file), and also a command line tool if you're so inclined (brew install flyway)

To run migrations:

```shell
mvn flyway:migrate
```

## Code Formatting

We use the Google Code Formatter. Builds will fail if your code is ugly (:grin: just kidding), but seriously - format your code before committing with:

```shell
mvn fmt:format
```

## Testing

The following will run all unit and integration tests, as well as check the code formatter. You'll notice that the `pom.xml` sets up some resources like a MySQL equivalent in-memory database called H2, and a redis instance that will automatically start and stop when running integration tests. So long as you have maven installed, you should be able to run tests without any other dependencies. 

```shell
mvn clean verify
```

## API

The following are some example requests you can make:

```shell
curl localhost:8080/api/redis/set -H 'Content-Type: application/json' -d '{"key": "my_key", "value": "my_value"}'
OK
```
```shell
curl localhost:8080/api/redis/get/my_key
my_value
```

```shell
curl localhost:8080/api/users -d ''
{"id":"e5aad22d-cd08-4143-9a5b-11bc02887613"}
```

```shell
curl localhost:8080/api/users/e5aad22d-cd08-4143-9a5b-11bc02887613
{"id":"e5aad22d-cd08-4143-9a5b-11bc02887613"}
```

```shell
curl -XDELETE localhost:8080/api/users/e5aad22d-cd08-4143-9a5b-11bc02887613
{"id":"e5aad22d-cd08-4143-9a5b-11bc02887613"}
```

```shell
curl localhost:8080/api/users/e5aad22d-cd08-4143-9a5b-11bc02887613 | jq
{
  "timestamp": 1520545062775,
  "status": 404,
  "error": "Not Found",
  "exception": "com.uken.platform.demo.exceptions.UserNotFound",
  "message": "User 'e5aad22d-cd08-4143-9a5b-11bc02887613' could not be found",
  "path": "/api/users/e5aad22d-cd08-4143-9a5b-11bc02887613"
}
```
