package edu.sjsu.cohort6.db.mongodb;

import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import com.mongodb.util.JSON;
import edu.sjsu.cohort6.common.PersonalRatings;
import edu.sjsu.cohort6.db.BaseDAO;
import edu.sjsu.cohort6.db.DBException;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.dao.BasicDAO;
import org.mongodb.morphia.mapping.Mapper;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.QueryResults;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

/**
 * @author rwatsh on 10/7/16.
 */
public class PersonalRatingsDAO extends BasicDAO<PersonalRatings, String> implements BaseDAO<PersonalRatings> {
    private static final Logger log = Logger.getLogger(PersonalRatingsDAO.class.getName());
    protected Morphia morphia;

    protected PersonalRatingsDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
        this.morphia = morphia;
    }
    @Override
    public List<String> add(List<PersonalRatings> entityList) throws DBException {
        List<String> insertedIds = new ArrayList<>();

        if (entityList != null) {
            for (PersonalRatings personalRating: entityList) {
                Key<PersonalRatings> key = this.save(personalRating);
                insertedIds.add(key.getId().toString());
            }
        }
        return insertedIds;
    }

    @Override
    public long remove(List<String> list) throws DBException {
        return 0;
    }

    @Override
    public void update(List<PersonalRatings> list) throws DBException {

    }

    @Override
    public List<PersonalRatings> fetchById(List<String> entityIdsList, Integer limit) throws DBException {
        List<String> objectIds = new ArrayList<>();
        Query<PersonalRatings> query =  null;

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
        QueryResults<PersonalRatings> results = this.find(query);
        return results.asList();
    }

    @Override
    public List<PersonalRatings> fetch(String query) throws DBException {
        List<PersonalRatings> personalRatingsList = new ArrayList<>();
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
            PersonalRatings rating = morphia.fromDBObject(PersonalRatings.class, dbObject);
            personalRatingsList.add(rating);
        }
        return personalRatingsList;
    }
}
