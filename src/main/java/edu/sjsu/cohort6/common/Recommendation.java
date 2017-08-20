package edu.sjsu.cohort6.common;

/**
 * This class wraps the contents from UserRecommendations and MovieRatings so returned response for client has
 * enough details to render the movie correctly. A list of this class instances will be returned to the user.
 *
 * @author rwatsh on 10/12/16.
 */
public class Recommendation extends BaseModel {
    private String title;
    private int rating;
    private String genre;
    private int movieId;
    private int imdbid;
    private MovieMetadata metadata;

    public MovieMetadata getMetadata() {
        return metadata;
    }

    public void setMetadata(MovieMetadata metadata) {
        this.metadata = metadata;
    }

    public Recommendation() {

    }

    public Recommendation(int movieId, String title, String genre, int rating, int imdbid) {
        this.movieId = movieId;
        this.rating = rating;
        this.genre = genre;
        this.title = title;
        this.imdbid = imdbid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

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

    public int getMovieId() {
        return movieId;
    }

    public int getImdbid() {
        return imdbid;
    }

    public void setImdbid(int imdbid) {
        this.imdbid = imdbid;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "title='" + title + '\'' +
                ", rating=" + rating +
                ", genre='" + genre + '\'' +
                ", movieId=" + movieId +
                ", imdbid=" + imdbid +
                ", metadata=" + metadata +
                '}';
    }
}
