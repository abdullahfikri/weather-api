# WEATHER API 1.0
Is REST API build from SPRING BOOT FRAMEWORK to get forecasting weather on a city in a country.

Project base on: **[Weather API
](https://roadmap.sh/projects/weather-api-wrapper-service)**

# Requirement
- Java 21 or higher
- Spring Boot version 3.4.4
- Maven 3.6 or higher
- Redis 7.4.2

# Feature
- Get the forecasting weather data from a city.
- Caching forecasting data to redis.

# How To Use
##### 1 . Clone the repository with the following command:
```
    git clone https://github.com/abdullahfikri/weather-api.git
    cd ./weather-api
```

##### 2. Setting Environment Variable to run the App
```
    export API_KEY=API_KEYS  #you can get the keys from https://www.visualcrossing.com/weather-api for free
    export REDIS_USERNAME=redis_username
    export REDIS_PASSWORD=redis_password
```

##### 3. You can the redis host or port or database configuration in file /src/main/resources/application.yaml

##### 4. Run the following command to build and run the app:
```
    mvn clean package
    java -jar target/weather-api-0.0.1-SNAPSHOT.war
```

##### 5. You can access documentation in format json: 
```
    http://localhost:8080/api-docs
```
 or Swagger UI 
```
    http://localhost:8080/swagger-ui/index.html
```


