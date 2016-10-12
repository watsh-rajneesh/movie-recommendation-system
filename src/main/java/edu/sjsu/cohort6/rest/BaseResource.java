/*
 * Copyright (c) 2015 San Jose State University.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 */

package edu.sjsu.cohort6.rest;

import edu.sjsu.cohort6.common.BaseModel;
import edu.sjsu.cohort6.db.DBClient;
import edu.sjsu.cohort6.db.mongodb.MovieRatingsDAO;
import edu.sjsu.cohort6.db.mongodb.PersonalRatingsDAO;
import edu.sjsu.cohort6.db.mongodb.UserRecommendationsDAO;
import edu.sjsu.cohort6.rest.exception.InternalErrorException;
import io.dropwizard.servlets.assets.ResourceNotFoundException;

import javax.validation.Valid;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by rwatsh on 9/23/15.
 */
public abstract class BaseResource<T extends BaseModel> {

    private DBClient dbClient;
    protected MovieRatingsDAO movieRatingsDAO;
    protected PersonalRatingsDAO personalRatingsDAO;
    protected UserRecommendationsDAO userRecommendationsDAO;

    public BaseResource(DBClient client) {
        this.dbClient = client;
        this.userRecommendationsDAO = (UserRecommendationsDAO) client.getDAO(UserRecommendationsDAO.class);
        this.movieRatingsDAO = (MovieRatingsDAO) client.getDAO(MovieRatingsDAO.class);
        this.personalRatingsDAO = (PersonalRatingsDAO) client.getDAO(PersonalRatingsDAO.class);
    }

    /*
     * Note: It is important to also define the @POST, @GET... etc annotations with the implementation methods in the
     * derived classes or else they will not be accounted for by dropwizard framework.
     */

    /**
     * Create the resource.
     *
     * @param modelJson
     * @param info
     * @return
     */
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    abstract public Response create(@Valid String modelJson, @Context UriInfo info);


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    abstract public List<T> list(@QueryParam("filter") String filter) throws InternalErrorException, UnsupportedEncodingException;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    abstract public  T retrieve(@PathParam("id") String id)
            throws ResourceNotFoundException, InternalErrorException;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    abstract public T update(@PathParam("id") String id,
                             @Valid String entity) throws ResourceNotFoundException, InternalErrorException, IOException;

    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{id}")
    abstract public Response delete(@PathParam("id") String id)
            throws ResourceNotFoundException, InternalErrorException;

    /**
     * Common methods
     */

    protected List<String> getListFromEntityId(String entityId) {
        List<String> entityIdsList = new ArrayList<>();

        if (entityId != null && !entityId.isEmpty()) {
            entityIdsList.add(entityId);
        } else {
            entityIdsList = null;
        }
        return entityIdsList;
    }

    protected List<T> getListFromEntity(T entity) {
        List<T> entitiesList = new ArrayList<>();

        if (entity != null) {
            entitiesList.add(entity);
        } else {
            entitiesList = null;
        }
        return entitiesList;
    }
}

