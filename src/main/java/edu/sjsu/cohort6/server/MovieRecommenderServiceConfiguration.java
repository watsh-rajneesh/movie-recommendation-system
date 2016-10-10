package edu.sjsu.cohort6.server;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.dropwizard.Configuration;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

/**
 * @author rwatsh on 10/9/16.
 */
public class MovieRecommenderServiceConfiguration extends Configuration {
    @Valid
    @NotNull
    private DBConfig dbConfig = new DBConfig();

    @JsonProperty("database")
    public DBConfig getDbConfig() {
        return dbConfig;
    }

    @JsonProperty("database")
    public void setDbConfig(DBConfig dbConfig) {
        this.dbConfig = dbConfig;
    }
}
