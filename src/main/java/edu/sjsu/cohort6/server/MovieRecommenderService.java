package edu.sjsu.cohort6.server;

import edu.sjsu.cohort6.db.DBClient;
import edu.sjsu.cohort6.rest.EndpointUtils;
import edu.sjsu.cohort6.rest.MoviesResource;
import edu.sjsu.cohort6.rest.RecommendationsResource;
import edu.sjsu.cohort6.spark.MovieRecommendation;
import io.dropwizard.Application;
import io.dropwizard.setup.Bootstrap;
import io.dropwizard.setup.Environment;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Movie recommendation service using Apache Spark and MongoDB.
 *
 * The dataset is data/movies.zip. Use mongorestore -d movies <path to data> to import the dataset to mongodb.
 *
 * When a user request is received with personal ratings for some movies, based on that input recommendation will be
 * generated of movies for that user. In the present implementation, there is no user profile maintained so any one can
 * anonymously use the service, provide his/her ratings for the list of movies presented and get recommendations based on
 * the choices made by the user.
 *
 * This is the entry point for the recommendation microservice.
 *
 * Part of the implementation has been derived from:
 * https://www.mongodb.com/blog/post/the-new-mongodb-connector-for-apache-spark-in-action-building-a-movie-recommendation-engine
 *
 *
 * @author rwatsh on 10/7/16.
 */
public class MovieRecommenderService extends Application<MovieRecommenderServiceConfiguration> {
    private DBClient dbClient;
    private static final Logger log = Logger.getLogger(MovieRecommenderService.class.getName());

    /**
     * Main method that will launch the service.
     *
     * @param args
     * @throws Exception
     */
    public static void main(final String[] args) throws Exception {
        try {
            new MovieRecommenderService().run(args);
        } catch (Exception e) {
            log.log(Level.SEVERE, "Error: ", e);
            while(true) {
                //hang on for debugging
                Thread.sleep(5000);
            }
        }
    }

    @Override
    public String getName() {
        return "MovieRecommenderService";
    }

    @Override
    public void initialize(final Bootstrap<MovieRecommenderServiceConfiguration> bootstrap) {
        /*
         * Register the static html contents to be served from /assets directory and accessible from browser from
         * http://<host>:<port>/esp
         *
         * TBD - if we were to host our web app.
         */
        /*bootstrap.addBundle(new AssetsBundle("/assets", "/esp", "index.html"));

        bootstrap.addCommand(new CreateUserCommand());
        bootstrap.addCommand(new ListUserCommand());*/

        MovieRecommendation.initialize();
    }



    @Override
    public void run(MovieRecommenderServiceConfiguration movieRecommenderServiceConfiguration, Environment environment) throws Exception {
        dbClient = movieRecommenderServiceConfiguration.getDbConfig().build(environment);

        /*
         * Register resources with jersey.
         */
        final MoviesResource moviesResource = new MoviesResource(dbClient);
        final RecommendationsResource recommendationsResource = new RecommendationsResource(dbClient);
        /*
         * Setup jersey environment.
         */
        environment.jersey().setUrlPattern(EndpointUtils.ENDPOINT_ROOT + "/*");
        environment.jersey().register(moviesResource);
        environment.jersey().register(recommendationsResource);
    }
}
