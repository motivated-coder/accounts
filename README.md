## Project Details
In this project there are 3 different microservices interacting with each other. Different Technologies are being used in this project like
apgateway, eurekaserver, configserver, Docker, Kubernetes, kafka, openfeign, spring security using keycloak Authorization server etc.
## Maven Commands used in the course

|     Maven Command       |     Description          |
| ------------- | ------------- |
| "mvn clean install -Dmaven.test.skip=true" | To generate a jar inside target folder |
| "mvn spring-boot:run" | To start a springboot maven project |
| "mvn spring-boot:build-image" | To generate a docker image using Buildpacks. No need of Dockerfile |
| "mvn compile jib:dockerBuild" | To generate a docker image using Google Jib. No need of Dockerfile |

