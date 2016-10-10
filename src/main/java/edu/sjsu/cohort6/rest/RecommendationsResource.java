package edu.sjsu.cohort6.rest;

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
import java.util.List;
import java.util.logging.Logger;

/**
 * @author rwatsh on 10/9/16.
 */
@Path("/recommendations")
@Produces(MediaType.APPLICATION_JSON)
public class RecommendationsResource extends BaseResource<UserRecommendations>{
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
    public List<UserRecommendations> list(@QueryParam("filter") String filter) throws InternalErrorException {
        /*
         * fetch all ids so null is passed, and no limit so get everything.
         */
        return userRecommendationsDAO.fetchById(null, null);
    }

    @Override
    public UserRecommendations retrieve(@PathParam("id") String id) throws ResourceNotFoundException, InternalErrorException {
        return null;
    }

    @Override
    public UserRecommendations update(@PathParam("id") String id, @Valid String entity) throws ResourceNotFoundException, InternalErrorException, IOException {
        return null;
    }

    @Override
    public Response delete(@PathParam("id") String id) throws ResourceNotFoundException, InternalErrorException {
        return Response.status(Response.Status.NOT_IMPLEMENTED).build();
    }


}
