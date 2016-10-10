package edu.sjsu.cohort6.rest;

import edu.sjsu.cohort6.common.CommonUtils;
import edu.sjsu.cohort6.common.MovieRatings;
import edu.sjsu.cohort6.common.PersonalRatings;
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
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author rwatsh on 10/9/16.
 */
@Path("/movies")
@Produces(MediaType.APPLICATION_JSON)
public class MoviesResource extends BaseResource<MovieRatings>{

    private static final Logger log = Logger.getLogger(MoviesResource.class.getName());

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
            List<PersonalRatings> personalRatingsList = CommonUtils.convertJsonArrayToList(modelJson, PersonalRatings.class);
            personalRatingsDAO.deleteByQuery(personalRatingsDAO.createQuery()); // drop all entries
            personalRatingsDAO.add(personalRatingsList); // add new user ratings
            MovieRecommendation.main(new String[]{});
        } catch(Exception e) {
            log.log(Level.SEVERE, "Error in adding student", e);
            throw new BadRequestException(e);
        }
        return Response.accepted().entity("Generating recommendations").build();
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
