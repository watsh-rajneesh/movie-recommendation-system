package edu.sjsu.cohort6.db.mongodb;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import edu.sjsu.cohort6.common.MovieRatings;
import edu.sjsu.cohort6.db.BaseDAO;
import edu.sjsu.cohort6.db.DBException;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author rwatsh on 10/7/16.
 */
public class MovieRatingsDAO extends BasicDAO<MovieRatings, String> implements BaseDAO<MovieRatings> {
    private static final Logger log = Logger.getLogger(MovieRatingsDAO.class.getName());
    protected Morphia morphia;

    protected MovieRatingsDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
        this.morphia = morphia;
    }

    @Override
    public List<String> add(List<MovieRatings> list) throws DBException {
        return null;
    }

    @Override
    public long remove(List<String> list) throws DBException {
        return 0;
    }

    @Override
    public void update(List<MovieRatings> list) throws DBException {

    }

    @Override
    public List<MovieRatings> fetchById(List<String> entityIdsList, Integer limit)  {
        List<Integer> objectIds = new ArrayList<>();
        Query<MovieRatings> query =  null;

        if (entityIdsList != null) {
            for (String id : entityIdsList) {
                if (id != null) {
                    //id = CommonUtils.sanitizeIdString(id);
                    objectIds.add(Integer.parseInt(id));
                }
            }
        }
        query = objectIds != null && !objectIds.isEmpty()
                ? this.createQuery().field("movieId").in(objectIds)
                : (limit != null ? this.createQuery().limit(limit) : this.createQuery());
        QueryResults<MovieRatings> results = this.find(query);
        return results.asList();
    }

    @Override
    public List<MovieRatings> fetch(String query) throws DBException {
        return fetch(query, null);
    }



    public List<MovieRatings> fetch(String query, Integer limit) throws DBException {
        List<MovieRatings> movieRatingsList = new ArrayList<>();
        DBObject dbObjQuery;
        DBCursor cursor;
        if (!(query == null)) {
            dbObjQuery = (DBObject) JSON.parse(query);
            if (limit != null) {
                cursor = this.getCollection().find(dbObjQuery).limit(limit);
            } else {
                cursor = this.getCollection().find(dbObjQuery);
            }
        } else {
            cursor = this.getCollection().find();
        }

        List<DBObject> dbObjects = cursor.toArray();
        for (DBObject dbObject: dbObjects) {
            MovieRatings rating = morphia.fromDBObject(MovieRatings.class, dbObject);
            movieRatingsList.add(rating);
        }
        return movieRatingsList;
    }
}
