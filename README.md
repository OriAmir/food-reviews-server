## Server Project
This project build using [Spring Boot WEB](https://spring.io/guides/gs/spring-boot/).

## Main concepts:
- Using H2 memory DB to store the data.
- Using Spring Boot Data JPA to access the db data.
- Using `Pageable` interface for pagination and filter the data

## How to run
1. Clone project
1. Open project in IntelliJ IDEA or any other editor you want.
1. Install dependencies and set config (should be automatically).
1. Download the data source file called `Reviews.csv`from this link: https://drive.google.com/file/d/1o5AJhrgpgOm9IZ-8nEjPK1xXJvpOLD17/view?usp=sharing
1. Copy thw download file into the project to: `src/main/resources` (Important: the file name have to be: `Reviews.csv` !).
1. Run the project.
1. Server run on localhost:8080 .
1. You can see the DB on localhost:8080/h2-console/ .
