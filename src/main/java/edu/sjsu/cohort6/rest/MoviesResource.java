package edu.sjsu.cohort6.rest;

import edu.sjsu.cohort6.common.*;
import edu.sjsu.cohort6.db.DBClient;
import edu.sjsu.cohort6.rest.exception.BadRequestException;
import edu.sjsu.cohort6.rest.exception.InternalErrorException;
import edu.sjsu.cohort6.spark.MovieRecommendation;
import io.dropwizard.servlets.assets.ResourceNotFoundException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rwatsh on 10/9/16.
 */
@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource extends BaseResource<MovieRatings>{

    private static final Logger log = Logger.getLogger(MoviesResource.class.getName());
    /**
     * Hard coded map of genre to movies. This is required because we don't have the user rate the movies on the client
     * and instead require user to rate the genre. So on the server side we use the genre rating as the rating for the
     * movies of that genre (by looking up this map by the specified genre). For each genre we maintain 2 random movies
     * of that genre.
     */
    private static final Map<String,List<Integer>> genreToMovieIdsMap;
    static {
        genreToMovieIdsMap = new HashMap<>();
        genreToMovieIdsMap.put("action", new ArrayList<Integer>(){{
            add(1287);
            add(1197);
        }});
        genreToMovieIdsMap.put("comedy", new ArrayList<Integer>(){{
            add(2797);
            add(2791);
        }});

        genreToMovieIdsMap.put("drama", new ArrayList<Integer>(){{
            add(1193);
            add(3408);
        }});

        genreToMovieIdsMap.put("horror", new ArrayList<Integer>(){{
            add(1690);
            add(1261);
        }});
    }

    public MoviesResource(DBClient client) {
        super(client);
    }
    /**
     * Create the resource.
     *
     * @param modelJson
     * @param info
     * @return
     */
    @Override
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(@Valid String modelJson, @Context UriInfo info) {
        try {
            /*
            PersonalRatings.json: -- this is what is expected by the APIs so we can create personal_ratings entries.
             [
                {"movieId" : 1193, "rating" : 5, "userId" : 0},
                {"movieId" : 661, "rating" : 3, "userId" : 0},
                {"movieId" : 2355, "rating" : 5, "userId" : 0},
                {"movieId" : 1287, "rating" : 4, "userId" : 0},
                {"movieId" : 914, "rating" : 2, "userId" : 0}
            ]

            UserChoices.json: -- this is what gets passed by client.
            [
                {"genre": "action", "rating": 1},
                {"genre": "horror", "rating": 4},
                {"genre": "comedy", "rating": 3},
                {"genre": "drama", "rating": 5}
            ]
             */
            //List<PersonalRatings> personalRatingsList = CommonUtils.convertJsonArrayToList(modelJson, PersonalRatings.class);
            List<UserChoices> userChoicesList = CommonUtils.convertJsonArrayToList(modelJson, UserChoices.class);
            List<PersonalRatings> personalRatingsList = new ArrayList<PersonalRatings>();
            if (userChoicesList != null) {
                for (UserChoices userChoices: userChoicesList) {
                    List<Integer> movieIds = genreToMovieIdsMap.get(userChoices.getGenre().toLowerCase());
                    if (movieIds != null) {
                        for (int id: movieIds) {
                            PersonalRatings pr = new PersonalRatings();
                            pr.setMovieId(id);
                            pr.setRating(userChoices.getRating());
                            pr.setUserId(0);
                            personalRatingsList.add(pr);
                        }
                     }
                }
            }
            personalRatingsDAO.deleteByQuery(personalRatingsDAO.createQuery()); // drop all entries
            personalRatingsDAO.add(personalRatingsList); // add new user ratings
            //MovieRecommendation.main(new String[]{});
            // clean up user_recommendations collection before beginning a new recommendation
            userRecommendationsDAO.deleteByQuery(userRecommendationsDAO.createQuery()); // drop all entries
            MovieRecommendation.recommend(); // this will create entries in the user_recommendations collection.
        } catch(Exception e) {
            log.log(Level.SEVERE, "Error in adding student", e);
            throw new BadRequestException(e);
        }
        return Response.accepted().build();
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<MovieRatings> list(@QueryParam("filter") String filter) throws InternalErrorException {
        // Always return top 10 movies
        return movieRatingsDAO.fetchById(null, 10);
    }


    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{movieId}")
    public MovieRatings retrieve(@PathParam("movieId") String id) throws ResourceNotFoundException, InternalErrorException {
        List<String> movieIds = new ArrayList<>();
        movieIds.add(id);
        return movieRatingsDAO.fetchById(movieIds, null).get(0);
    }

    @Override
    public MovieRatings update(@PathParam("id") String id, @Valid String entity) throws ResourceNotFoundException, InternalErrorException, IOException {
        return null;
    }

    @Override
    public Response delete(@PathParam("id") String id) throws ResourceNotFoundException, InternalErrorException {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }
}
