## Server Project
This project build using [Spring Boot WEB](https://spring.io/guides/gs/spring-boot/).

## Main concepts:
The idea is to septate business logic pages inside the Components folder.

- Using H2 memory DB to store the data.
- Using Spring Boot Data JPA to access the db data.
- Using `Pageable` interface for pagination and filter the data

## How to run
1. Clone project
1. Open project in IntelliJ IDEA or any other editor you want.
1. Wait for installation.
1. Download the `Reviews.csv` file from here: https://drive.google.com/file/d/1o5AJhrgpgOm9IZ-8nEjPK1xXJvpOLD17/view?usp=sharing
1. set this files inside `src/main/resources`.
1. Run the project
1. Server run on localhost:8080
1. You can see the DB on localhost:8080/h2-console/
