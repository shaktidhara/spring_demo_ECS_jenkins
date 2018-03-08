# Spring Demo

This application is intended to demonstrate some common pieces that are frequently used by Spring application at Uken Games. Some of these pieces include:
- Our common parent POM including:
  - Datadog APM and custom metrics
  - Common Spring / Spring boot starters
  - a code formatter
- MySQL database adapter, including Flyway DB for running migrations
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
