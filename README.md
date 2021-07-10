# meli-challenge 
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
$ docker-compose up
``` 

### **Using `Maven`** (with local Postgres database)

First define the environment variables with values of your database:

Windows
```console
$ set POSTGRES_SERVER=localhost
$ set POSTGRES_DB=meli
$ set POSTGRES_USER=meli
$ set POSTGRES_PASSWORD=123
```
Linux or MacOS
```console
$ export POSTGRES_SERVER=localhost
$ export POSTGRES_DB=meli
$ export POSTGRES_USER=meli
$ export POSTGRES_PASSWORD=123
```

Then just run:

```console
$ mvnw clean compile quarkus:dev
```

Access the documentation: 

http://localhost:8090/index.html

## Access via Google Cloud Run
