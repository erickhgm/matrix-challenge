# matrix-challenge
> Written in Hexagonal Architecture

This is a small API that provides basic REST endpoints to know if a DNA sequence belongs to a Simion and to gets statistics.

The technology behind it: 
* Java 11
* Quarkus
* Postgres

## Installing / Getting started

### **Using `docker-compose`**

In the terminal run the following command:
```console
docker-compose up
``` 

### **Using `Maven`** (with local Postgres database)

First define the environment variables with values of your database:

Windows
```console
set POSTGRES_SERVER=localhost
set POSTGRES_DB=matrix
set POSTGRES_USER=matrix
set POSTGRES_PASSWORD=123
```
Linux or MacOS
```console
export POSTGRES_SERVER=localhost
export POSTGRES_DB=matrix
export POSTGRES_USER=matrix
export POSTGRES_PASSWORD=123
```

Then just run:

Windows
```console
mvnw clean compile quarkus:dev
```
Linux or MacOS
```console
./mvnw clean compile quarkus:dev
```

### **Access the documentation**
After starting the app using the `docker-compose` or `Maven` you can access the documentation and test using the `Try it on` option.

http://localhost:8090/index.html


### **Running tests** (with embedded H2 database)

In the terminal run the following command:

Windows
```console
mvnw clean verify
```
Linux or MacOS
```console
./mvnw clean verify
```

Then access the coverage report in:
```console
target/jacoco-report/index.html
```

## Public access via Google Cloud Run
https://matrix-challenge-jlyaiwmgpq-uc.a.run.app/index.html
