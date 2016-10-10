package edu.sjsu.cohort6.db.mongodb;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import edu.sjsu.cohort6.common.UserRecommendations;
import edu.sjsu.cohort6.db.BaseDAO;
import edu.sjsu.cohort6.db.DBException;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author rwatsh on 10/9/16.
 */
public class UserRecommendationsDAO extends BasicDAO<UserRecommendations, String> implements BaseDAO<UserRecommendations> {
    private static final Logger log = Logger.getLogger(PersonalRatingsDAO.class.getName());
    protected Morphia morphia;

    protected UserRecommendationsDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
        this.morphia = morphia;
    }

    @Override
    public List<String> add(List<UserRecommendations> entityList) throws DBException {
        return null;
    }

    @Override
    public long remove(List<String> entityIdsList) throws DBException {
        return 0;
    }

    @Override
    public void update(List<UserRecommendations> entityList) throws DBException {

    }

    @Override
    public List<UserRecommendations> fetchById(List<String> entityIdsList, Integer limit)  {
        List<String> objectIds = new ArrayList<>();
        Query<UserRecommendations> query =  null;

        if (entityIdsList != null) {
            for (String id : entityIdsList) {
                if (id != null) {
                    //id = CommonUtils.sanitizeIdString(id);
                    objectIds.add(id);
                }
            }
        }
        query = objectIds != null && !objectIds.isEmpty()
                ? this.createQuery().field(Mapper.ID_KEY).in(objectIds)
                : (limit != null ? this.createQuery().limit(limit) : this.createQuery());
        QueryResults<UserRecommendations> results = this.find(query);

        return results.asList();
    }

    @Override
    public List<UserRecommendations> fetch(String query) throws DBException {
        List<UserRecommendations> userRecommendationsList = new ArrayList<>();
        DBObject dbObjQuery;
        DBCursor cursor;
        if (!(query == null)) {
            dbObjQuery = (DBObject) JSON.parse(query);
            cursor = this.getCollection().find(dbObjQuery);
        } else {
            cursor = this.getCollection().find();
        }

        List<DBObject> dbObjects = cursor.toArray();
        for (DBObject dbObject: dbObjects) {
            UserRecommendations rating = morphia.fromDBObject(UserRecommendations.class, dbObject);
            userRecommendationsList.add(rating);
        }
        return userRecommendationsList;
    }
}
