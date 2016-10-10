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

package edu.sjsu.cohort6.db.mongodb;

import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.mongodb.MongoClient;
import edu.sjsu.cohort6.common.MovieRatings;
import edu.sjsu.cohort6.db.BaseDAO;
import edu.sjsu.cohort6.db.DBClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import java.text.MessageFormat;

/**
 * Created by rwatsh on 9/20/15.
 */
public class MongoDBClient implements DBClient {
    private final String server;
    private final int port;
    private final String dbName;
    private MovieRatingsDAO movieRatingsDAO;
    private PersonalRatingsDAO personalRatingsDAO;
    private UserRecommendationsDAO userRecommendationsDAO;
    private Morphia morphia = null;
    private MongoClient mongoClient;
    private Datastore morphiaDatastore;

    /**
     * Constructs a MongoDB client instance.
     *
     * This is private so it can only be instantiated via DI (using Guice).
     *
     * @param server    server hostname or ip
     * @param port      port number for mongodb service
     * @param dbName    name of db to use
     */
    @Inject
    private MongoDBClient(@Assisted("server") String server, @Assisted("port") int port, @Assisted("dbName") String dbName) {
        this.server = server;
        this.port = port;
        this.dbName = dbName;
        mongoClient = new MongoClient(server, port);

        morphia = new Morphia();
        morphia.mapPackageFromClass(MovieRatings.class);
        morphiaDatastore = morphia.createDatastore(mongoClient, dbName);
        morphiaDatastore.ensureIndexes();
        movieRatingsDAO = new MovieRatingsDAO(mongoClient, morphia, dbName);
        personalRatingsDAO = new PersonalRatingsDAO(mongoClient, morphia, dbName);
        userRecommendationsDAO = new UserRecommendationsDAO(mongoClient, morphia, dbName);
    }

    @Override
    public void dropDB(String dbName) {
        morphiaDatastore.getDB().dropDatabase();
    }

    @Override
    public void useDB(String dbName) {
        morphiaDatastore = morphia.createDatastore(mongoClient, dbName);
        morphiaDatastore.ensureIndexes();
    }

    @Override
    public boolean checkHealth() {
        try {
            mongoClient.listDatabaseNames();
            return true;
        } catch(Exception e) {
            return false;
        }
    }

    @Override
    public String getConnectString() {
        return MessageFormat.format("DB Connect Info: server [{0}], port [{1}], dbName [{2}]", server, port, dbName);
    }

    @Override
    public Object getDAO(Class<? extends BaseDAO> clazz) {
        if (clazz != null) {
            if (clazz.getTypeName().equalsIgnoreCase(MovieRatingsDAO.class.getTypeName())) {
                return movieRatingsDAO;
            } else if (clazz.getTypeName().equalsIgnoreCase(PersonalRatingsDAO.class.getTypeName())) {
                return personalRatingsDAO;
            } else if (clazz.getTypeName().equalsIgnoreCase(UserRecommendationsDAO.class.getTypeName())) {
                return userRecommendationsDAO;
            }
        }
        return null;
    }

    @Override
    public Morphia getMorphia() {
        return morphia;
    }

    @Override
    public void close() throws Exception {
        mongoClient.close();
    }

    @Override
    public String toString() {
        return "MongoDBClient{" +
                "server='" + server + '\'' +
                ", port=" + port +
                ", dbName='" + dbName + '\'' +
                '}';
    }
}
