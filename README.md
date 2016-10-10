# Movie Recommendation System

A movie recommender system using mongo DB and Apache Spark.

Following are the APIs and sample movie recommendation:
   ``` 
    GET     /api/v1.0/movies 
    POST    /api/v1.0/movies 
    GET     /api/v1.0/movies/{movieId} 
    GET     /api/v1.0/recommendations
   ```
    
 The POST payload for /api/v1.0/movies is of the form: 
```
 [{"genre":"Adventure", "rating": 1}, 
{"genre": "Action", "rating": 2"}, …]
```

You can build it as:
```
movie-recommender> mvn clean install -DskipTests
movie-recommender> java -jar target/movie-recommender-1.0-SNAPSHOT.jar server config.yml
```

This will start the web service at port 8080. You can access the REST endpoints in browser by going to [http://localhost:8080/api/v1.0/movies](http://localhost:8080/api/v1.0/movies) or [http://localhost:8080/api/v1.0/recommendations](http://localhost:8080/api/v1.0/recommendations) or [http://localhost:8080/api/v1.0/movies/{movieId}](http://localhost:8080/api/v1.0/movies/{movieId})

The config.yml file is where the DB and web service co-ordiantes are defined.
