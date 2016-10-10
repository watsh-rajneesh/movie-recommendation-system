package edu.sjsu.cohort6.common;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * @author rwatsh on 10/9/16.
 */
@Entity(value = "movie_ratings" , noClassnameStored = true, concern = "SAFE")
public class MovieRatings extends BaseModel {
    @Id
    private String id = new ObjectId().toHexString();
    @Property("movie_id")
    private Integer movieId;
    private Integer rating;
    private String title;
    private String genre;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id != null ? new ObjectId(id).toHexString() : new ObjectId().toHexString();
    }

    public Integer getMovieId() {
        return movieId;
    }

    public void setMovieId(Integer movieId) {
        this.movieId = movieId;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "MovieRatings{" +
                "id='" + id + '\'' +
                ", movieId=" + movieId +
                ", rating=" + rating +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
