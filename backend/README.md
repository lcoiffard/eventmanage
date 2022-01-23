=======================

# Event management backend

### Swagger

http://localhost:8080/swagger-ui/index.html#

### Prerequis

* Java 11

## Build project

mvn clean install

## Build docker image

docker build --tag=eventmanage-backend:latest .

## Run docker image

docker run -p8080:8080 eventmanage-backend:latest 