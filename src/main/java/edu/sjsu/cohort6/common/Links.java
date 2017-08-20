package edu.sjsu.cohort6.common;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

/**
 * @author rwatsh on 10/12/16.
 */
@Entity(value = "links" , noClassnameStored = true, concern = "SAFE")
public class Links extends BaseModel {
    @Id
    private String id = new ObjectId().toHexString();
    private int movieId;
    private int imdbId;
    private int tmdbId;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id != null ? new ObjectId(id).toHexString() : new ObjectId().toHexString();
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getImdbId() {
        return imdbId;
    }

    public void setImdbId(int imdbId) {
        this.imdbId = imdbId;
    }

    public int getTmdbId() {
        return tmdbId;
    }

    public void setTmdbId(int tmdbId) {
        this.tmdbId = tmdbId;
    }

    @Override
    public String toString() {
        return "Links{" +
                "id='" + id + '\'' +
                ", movieId=" + movieId +
                ", imdbId=" + imdbId +
                ", tmdbId=" + tmdbId +
                '}';
    }
}
