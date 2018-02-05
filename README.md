
## How to Run

##### Requirements
* JDK 1.8
* Maven 3.x
* build the project by running ```mvn clean package```

###### Run the application
```
        java -jar -Dspring.profiles.active=test target/spring-boot-rest-1.0.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```

### Information about system health, configurations, etc.

```
http://localhost:9001/env
http://localhost:9001/health
http://localhost:9001/info
http://localhost:9001/metrics
```

### Swagger APIs

```
localhost:9000/swagger-ui.html
