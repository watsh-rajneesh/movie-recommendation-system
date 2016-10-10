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

package edu.sjsu.cohort6.server;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.inject.Guice;
import com.google.inject.Inject;
import com.google.inject.Module;
import edu.sjsu.cohort6.db.DBClient;
import edu.sjsu.cohort6.db.DBFactory;
import edu.sjsu.cohort6.db.DatabaseModule;
import io.dropwizard.lifecycle.Managed;
import io.dropwizard.setup.Environment;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.logging.Logger;

/**
 * Created by rwatsh on 9/17/15.
 *
 *
 *
 */
public class DBConfig {
    @Inject
    DBFactory dbFactory;

    @NotEmpty
    private String server = "localhost";

    @Min(1)
    @Max(65535)
    private int port = 27017;

    @NotEmpty
    private String dbName = "movies";
    private DBClient dbClient;
    private static final String MONGO_PORT_ENV = "MONGO_PORT_27017_TCP_PORT";
    private static final String MONGO_HOST_ENV = "MONGO_PORT_27017_TCP_ADDR";
    private static final Logger log = Logger.getLogger(DBConfig.class.getName());

    @JsonIgnore
    public DBClient getDbClient() {
        return dbClient;
    }

    @JsonProperty
    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    @JsonProperty
    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    @JsonProperty
    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public DBClient build(Environment environment) {
        environment.lifecycle().manage(new Managed() {
            @Override
            public void start() throws Exception {
                dbClient.useDB(dbName);
            }

            @Override
            public void stop() throws Exception {
                dbClient.close();
            }
        });
        return dbClient;
    }

    public DBConfig() {
        Module module = new DatabaseModule();
        Guice.createInjector(module).injectMembers(this);
        /**
         * The below environment variables are set by docker.
         * Assumption is the name for mongodb link is "mongo" so
         * --link <mongodb image name>: mongo
         */
        String dbHost = System.getenv(MONGO_HOST_ENV);
        String dbPort = System.getenv(MONGO_PORT_ENV);
        if (dbHost != null && dbPort != null) {
            log.info("Using docker specified DB host: " + dbHost);
            log.info("Using docker specified DB port: " + dbPort);
            dbClient = dbFactory.create(dbHost, Integer.parseInt(dbPort), dbName);
        } else {
            log.info("Non docker environment, using DB host and port as: [" + server + ", " + port + "]");
            dbClient = dbFactory.create(server, port, dbName);
        }
    }
}
