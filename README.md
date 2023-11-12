# Barbenheimer-Project



## Requirements

For building and running the application you need:

- [JDK 17](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Maven 3](https://maven.apache.org)
- [Docker](https://docs.docker.com/get-docker/)
- [MySQL](https://dev.mysql.com/downloads/mysql/) 
- Any IDE, our team utilised [Intellij](https://www.jetbrains.com/idea/) and [VSCode](https://code.visualstudio.com/)
- Setup your IDE to work with Spring using [Spring Setup Guide](https://spring.io/guides/gs/spring-boot/)

## Setup your environment
 

### Setup DB
Run mysql and access mysql management system
```shell
mysql -u <username> -p
```
Create a barbenheimer database for the application
```mysql
CREATE DATABASE barbenheimer;
```

### Environment Variables

The following environment variables are seperated per application, example values assumes the application is running locally or based on default values.
Alter sensitive values such as password in actual deployment. 
#### Spring: Api Gateway
```shell
DISCOVERY_PASSWORD=Password;
DISCOVERY_SERVER_HOST=localhost;
DISCOVERY_SERVER_PORT=8761;
DISCOVERY_USERNAME=admin;
```

#### Sprint: Discovery Server
```shell
DISCOVERY_PASSWORD=Password;
DISCOVERY_USERNAME=admin;
```

#### Spring: Mailer Service
```shell
DISCOVERY_PASSWORD=Password;
DISCOVERY_SERVER_HOST=localhost;
DISCOVERY_SERVER_PORT=8761;
DISCOVERY_SERVER_URL=http://localhost:8761/eureka;
DISCOVERY_USERNAME=admin;
MAIL_EMAIL=barbenheimer203@gmail.com;
MAIL_HOST=smtp.gmail.com;
MAIL_PASSWORD=<mail-password>;
MAIL_PORT=465;
```

#### Spring: Movie Service
```shell
AWS_BUCKET=<aws-bucket>;
AWS_DEFAULT_REGION=<aws-region>;
DATABASE_HOST=localhost;
DATABASE_PASSWORD=<db-password>;
DATABASE_PORT=3306;
DATABASE_SCHEMA=barbenheimer;
DATABASE_USERNAME=<db-user>;
DISCOVERY_PASSWORD=Password;
DISCOVERY_SERVER_HOST=localhost;
DISCOVERY_SERVER_PORT=8761;
DISCOVERY_SERVER_URL=http://localhost:8761/eureka;
DISCOVERY_USERNAME=admin;
STRIPE_PRIVATE_KEY=<stripe-private-key>;
WEBHOOK_SECRET=<stripe-webhook-secret>;
```


## Running the application locally

Start Docker Daemon

Start mysql using either MySQL/Xampp/Wamp/Mamp

Begin with running the docker-compose.yml using the following commands at these 2 directories
- barbenheimer/docker-compose.yml
- barbenheimer/userserver/docker-compose.yml
```shell
docker compose up -d
```

Now run 4 spring applications using the following command in order of these 4 directories (Ensure ./mvnw is executable)
Note: Run api-gateway after all services are up and running
- barbenheimer/discovery-server/
- barbenheimer/mailer-service/
- barbenheimer/movie-service/
- barbenheimer/api-gateway/
```shell
./mvnw spring-boot:run
```

### Others

Here is the list of links to the management systems
- IAM System - Keycloak: http://localhost:8181/ (admin:admin)
- Discovery/LB Server - Eureka: http://localhost:8761/ (admin:Password)
- Distributed Tracing System - Zipkin: http://localhost:9411/


## Common Issues

### Service Unavailable
If any request to api-gateway results in an error status 503 followed by this response
```json
{
    "timestamp": "2023-11-09T12:01:21.919+00:00",
    "path": "/api/v1/movies",
    "status": 503,
    "error": "Service Unavailable",
    "requestId": "f75eb1a6-1"
}
```
Rerun the api-gateway it is possible that the api-gateway began running before the other services have finish starting up

### No permission to execute ./mvnw
Allow mvnw to be executable using the following command
```shell
chmod +x mvnw
```

## Deployment

### Amazon EC2
The dockerized backend has been deployed onto a t2.large instance on EC2. Attached is a picture of the results returned when calling the endpoint /api/v1/movies.

![image](https://github.com/EddyEhhh/barbenheimer-backend/assets/120258509/1a80bc58-3eec-46f9-9055-8c455538491a)

Due to budget restrictions, the instance will not be up 24/7. 
