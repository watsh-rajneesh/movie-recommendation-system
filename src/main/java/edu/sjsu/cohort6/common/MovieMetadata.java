package edu.sjsu.cohort6.common;

import lombok.Data;

/**
 * This class maps to the data returned from Simapi for getting imdb data.
 *
 * Ideally we would store it all locally in mongodb.
 *
 * For example:
 *
 * {
 "id": "116126",
 "title": "Don't Be a Menace to South Central While Drinking Your Juice in the Hood",
 "director": "Frank Davis,W. Alexander Ellis,Simone Farber,Evan Gilner,Jim Goldthwait,David Ticotin,Steven Tramz,Tony Lee,Andrea Reed,Robi Reed,Paris Barclay,",
 "writer": "Shawn Wayans,Marlon Wayans,Phil Beauman,",
 "cined": "Russ Brandt,",
 "prod": "Mark Burg,Dan Genetti,Eric L. Gold,Carrie Morrow,Cristal Rivera-Mitchel,Keenen Ivory Wayans,Marlon Wayans,Shawn Wayans,",
 "type": "movie",
 "year": "1996",
 "countries": "USA,",
 "dur": "89,94::(2005 unrated),",
 "mpaa": "Rated R for strong language, sexuality, some drug content and violence",
 "rate": "6.6",
 "cov": "https://images-na.ssl-images-amazon.com/images/M/MV5BMTA3NjQ1Mzg4NTReQTJeQWpwZ15BbWU3MDcwNTQwMzE@.jpg",
 "gen": "Comedy,Crime,",
 "plot": "Don't Be a Menace to South Central While Drinking your Juice in the Hood is a parody of several U.S. films about being in the 'Hood', for instance \"Boyz n the Hood\", \"South Central\", \"Menace II Society\", \"Higher Learning\" and \"Juice\". We follow Ashtray as he returns to the place he grew up in and meet his father and his basket-case friends. Crazy stuff happens. For example, Ashtray is older than his father and his best friend Loc Dog's grandmother is a trigger-happy old lady who blames her eccentric-looking kid for not being tough enough.",
 "plotout": "Don't Be a Menace to South Central While Drinking your Juice in the Hood is a parody of several U.S...",
 "cast": "Shawn Wayans,Marlon Wayans,Tracey Cherelle Jones,Chris Spencer,Suli McCullough,Darrel Heath,Helen Martin,Isaiah Barnes,Lahmard J. Tate,Keenen Ivory Wayans,Keith Morris,Craig Wayans,Casey Lee,Joe Scott,Kim Wayans,Vivica A. Fox,Leonette Scott,Marian Reynolds,Tommy Morgan Jr.,Virginia Watson,Gabriel Alexander,Scott Randle,Wesley Eugene,Tedero Jones Jr.,Queline Young,Alex Thomas,Reginald Green,Samuel Monroe Jr.,Kwam\u00e9 Gammon,Warren Washington,Mazi Mitchell,Benjamin N. Everitt,Toshi Toda,Tamayo Otsuki,Ahmad Reese,Lester Barrie,Vivian Smallwood,Omar Epps,Jeffrey Anderson-Gunter,Cynthia Madvig,James Van Patten,Alan Abelew,Michael Adler,Don Reed,Faizon Love,Bernie Mac,Mik Scriba,Kirk Kinder,A.J. Jamal,Antonio Fargas,LaWanda Page,Yvette Wilson,Guy Torry,Travon Jamar,Damien Dante Wayans,Charles Edward Bennett,Paula Jai Parker,Lisa Morgan,Tiara English,Terri J. Vaughn,Xavier Cook,Mitchell Marchand,J.W. Smith,Kelly Vaughn,Lexie Bigham,Lloyd Avery II,Michele L. Jennings,Krysten Leigh Jones,Mba Shakoor,"
 }
 * @author rwatsh on 10/12/16.
 */
@Data
public class MovieMetadata extends BaseModel {
    private int id; // same as imdbid in links collection
    private String title;
    private String director;
    private String writer;
    private String cined;
    private String prod;
    private String type;
    private String year;
    private String countries;
    private String dur; // duration of the movie
    private String mpaa;
    private String rate; // out of 10
    private String cov; // cover image of the movie - generally jpg format.
    private String gen; // type of movie
    private String plot;
    private String plotout;
    private String cast;
}
