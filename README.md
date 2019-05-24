# cnj-persistence-sql

Showcase of a simple cloud native Java application using JPA-based persistence to persist its domain model in
a PostgreSQL database.

The actual integration of JPA-based persistence is demonstrated with three different Java backend technologies:
* Java EE (see: cnj-persistence-sql-backend-javaee)
* Eclipse MicroProfile (see: cnj-persistence-sql-backend-micro)
* Spring Boot + Spring Data (see: cnj-persistence-sql-backend-spring)

In this showcase, KeyCloak is used as an OpenID Connect Authentication Provider. 

## Status
![Build status](https://drone.at.automotive.msg.team/api/badges/cloudtrain/cnj-persistence-sql/status.svg)

## Build this showcase 

### Prerequisites

In order to run the build, you have to install the following tools locally:
* Docker
* Docker Compose 
* Maven
* Java JDK 8

### Run Maven

You can build all showcase applications by running Maven:
```
mvn clean install -P pre-commit-stage
```

The Maven build will create Docker images for all showcase applications and run system tests on them.