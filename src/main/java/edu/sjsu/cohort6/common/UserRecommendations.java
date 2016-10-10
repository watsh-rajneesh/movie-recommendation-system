package edu.sjsu.cohort6.common;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

/**
 * @author rwatsh on 10/9/16.
 */
@Entity(value = "user_recommendations" , noClassnameStored = true, concern = "SAFE")
public class UserRecommendations extends BaseModel {
    @Id
    private String id = new ObjectId().toHexString();
    @Property("movie_id")
    private Integer movieId;
    private Integer rating;
    @Property("user_id")
    private Integer userId;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserRecommendations{" +
                "id='" + id + '\'' +
                ", movieId=" + movieId +
                ", rating=" + rating +
                ", userId=" + userId +
                '}';
    }
}
