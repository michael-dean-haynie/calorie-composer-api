# Calorie Composer API

A Spring Boot RESTful API with endpoints for CRUD operations on calorie-composer.codetudes.com data.

## Also see
[Calorie Composer UI](https://github.com/michael-dean-haynie/calorie-composer-ui)

## Local Development Setup
1. Use [calorie_composer_schema_init.sql](./sql/calorie_composer_schema_init.sql) to initialize calorie_composer database (mysql)
1. Make a copy of [application.yml.example](.src/main/resources/application.yml.example), place in project root, remove `.example` off the end of the file name and update the configurations as necessary for your environment
    * `db-seeding.*` properties will govern test data seeding on startup
        * `seed-on-startup`: true to enable test data seeding
        * `seed-fdc-data:`: true to pull data from FoodDataCentral API for test data
        * `seed-json-data`: true to create test data from hardcoded json data
        * `truncate-tables-first`: true to truncate all database tables before seeding test data on startup
1. Build
1. Run

## Build
From root: `mvn clean install`

## Run
From root: `mvn spring-boot:run`
