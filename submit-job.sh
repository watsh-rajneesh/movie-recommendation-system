#!/usr/bin/env zsh

DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
cd ${DIR}

mvn clean package -DskipTests
export SPARK_HOME=/Users/rwatsh/Software/spark-2.0.0-bin-hadoop2.7
$SPARK_HOME/bin/spark-submit \
  --class edu.sjsu.cohort6.spark.MovieRecommenderService \
  --master local \
  --packages org.mongodb.spark:mongo-spark-connector_2.11:0.4 \
  ${DIR}/target/movie-recommender-1.0-SNAPSHOT.jar