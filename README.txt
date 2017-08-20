Movie Recommendation System

A movie recommender system using mongo DB and Apache Spark.

Following are the APIs and sample movie recommendation:

    GET     /api/v1.0/movies 
    POST    /api/v1.0/movies 
    GET     /api/v1.0/movies/{movieId} 
    GET     /api/v1.0/recommendations
The POST payload for /api/v1.0/movies is of the form:

 [{"genre":"Adventure", "rating": 1}, 
{"genre": "Action", "rating": 2"}, …]
You can build it as:

movie-recommender> mvn clean install -DskipTests
Before we can run it, we need to install mongo DB and import the movielens.org dataset to 
mongo DB.

The dataset can be downloaded from: 
http://files.grouplens.org/datasets/movielens/ml-latest-small.zip

Extract the files and import to mongo DB as:

mongoimport -d movies -c links --type csv --file locations.csv --headerline --numinsertionWorkers 8080

PS: We only need links.csv from this dataset.

The other 3 csv files (movies.csv, ratings.csv and tags.csv are not needed.)

Then get the following dataset zip:

https://github.com/samweaver/mongodb-spark/blob/master/movie-recommendations/data/movies.zip

and extract it to a folder and run the following command to import the dataset to mongoDB.

mongorestore -d movies

At the end of the operations we have the following collections in movies db of mongodb: links movie_ratings personal_ratings

To run:

movie-recommender> java -jar target/movie-recommender-1.0-SNAPSHOT.jar server config.yml

This will start the web service at port 8080. 
You can access the REST endpoints in browser by going to 
http://localhost:8080/api/v1.0/movies or 
http://localhost:8080/api/v1.0/recommendations or 
http://localhost:8080/api/v1.0/movies/{movieId}

The config.yml file is where the DB and web service co-ordiantes are defined. When client 
does a POST on /api/v1.0/movies new user recommendations are persisted in mongo DB in 
user_recommendations collection.