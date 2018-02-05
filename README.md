
## How to Run 

This application is packaged as a war which has Tomcat 8 embedded. No Tomcat or JBoss installation is necessary. You run it using the ```java -jar``` command.

* Clone this repository 
* Make sure you are using JDK 1.8 and Maven 3.x
* You can build the project and run the tests by running ```mvn clean package```
* Once successfully built, you can run the service by one of these two methods:
```
        java -jar -Dspring.profiles.active=test target/spring-boot-rest-example-0.4.0.war
or
        mvn spring-boot:run -Drun.arguments="spring.profiles.active=test"
```

### Get information about system health, configurations, etc.

```
http://localhost:9001/env
http://localhost:9001/health
http://localhost:9001/info
http://localhost:9001/metrics
```

