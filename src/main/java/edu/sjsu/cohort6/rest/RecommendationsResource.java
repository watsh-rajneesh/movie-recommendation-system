package edu.sjsu.cohort6.rest;

import edu.sjsu.cohort6.common.Links;
import edu.sjsu.cohort6.common.MovieRatings;
import edu.sjsu.cohort6.common.Recommendation;
import edu.sjsu.cohort6.common.UserRecommendations;
import edu.sjsu.cohort6.db.DBClient;
import edu.sjsu.cohort6.rest.exception.InternalErrorException;
import io.dropwizard.servlets.assets.ResourceNotFoundException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author rwatsh on 10/9/16.
 */
@Path("/recommendations")
@Produces(MediaType.APPLICATION_JSON)
public class RecommendationsResource extends BaseResource<Recommendation>{
    private static final Logger log = Logger.getLogger(MoviesResource.class.getName());

    public RecommendationsResource(DBClient client) {
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
    public Response create(@Valid String modelJson, @Context UriInfo info) {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }

    @Override
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Recommendation> list(@QueryParam("filter") String filter) throws InternalErrorException {
        /*
         * fetch all ids so null is passed, and no limit so get everything.
         *
         * TODO only returning 10 for now.
         */
        List<UserRecommendations> userRecommendationsList = userRecommendationsDAO.fetchById(null, 10);
        List<Recommendation> recommendationList = new ArrayList<>();
        if (userRecommendationsList != null) {


            for (UserRecommendations ur : userRecommendationsList) {
                List<MovieRatings> movieRatingsList = movieRatingsDAO.fetchById(new ArrayList<String>() {{
                    add("" + ur.getMovieId());
                }}, null);

                List<Links> linksList = linksDAO.fetchById(new ArrayList<String>() {{
                    add("" + ur.getMovieId());
                }}, null);

                if (movieRatingsList != null && !movieRatingsList.isEmpty() &&
                        linksList != null &&  !linksList.isEmpty()) {
                    MovieRatings m = movieRatingsList.get(0);
                    Links l = linksList.get(0);
                    Recommendation r = new Recommendation(ur.getMovieId(), m.getTitle(),
                            m.getGenre(), m.getRating(), l.getImdbId());
                    // Get additional movie meta data from moviesapi.com
                    if (l.getImdbId() > 0) {
                        //String movieUri = MessageFormat.format(CommonUtils.MOVIES_API_FORAMT, Integer.toString(l.getImdbId()));
                        //MovieMetadata metadata = RestClientUtil.get(movieUri, MovieMetadata.class);
                        //r.setMetadata(metadata);
                    }
                    recommendationList.add(r);
                }
            }
        }
        return recommendationList;
    }

    @Override
    public Recommendation retrieve(@PathParam("id") String id) throws ResourceNotFoundException, InternalErrorException {
        return null;
    }

    @Override
    public Recommendation update(@PathParam("id") String id, @Valid String entity) throws ResourceNotFoundException, InternalErrorException, IOException {
        return null;
    }

    @Override
    public Response delete(@PathParam("id") String id) throws ResourceNotFoundException, InternalErrorException {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }


}
