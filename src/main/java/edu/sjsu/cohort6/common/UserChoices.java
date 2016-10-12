package edu.sjsu.cohort6.common;

/**
 * User preferences received from mobile app in the form:
 *
 * [
 {"genre": "action", "rating": 1},
 {"genre": "horror", "rating": 4},
 {"genre": "comedy", "rating": 3},
 {"genre": "drama", "rating": 5}
 ]
 *
 * @author rwatsh on 10/10/16.
 */
public class UserChoices extends BaseModel {
    private int rating;
    private String genre;

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    @Override
    public String toString() {
        return "UserChoices{" +
                "rating=" + rating +
                ", genre='" + genre + '\'' +
                '}';
    }
}
