package edu.sjsu.cohort6.db.mongodb;

import com.mongodb.MongoClient;
import edu.sjsu.cohort6.common.Links;
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
 * @author rwatsh on 10/12/16.
 */
public class LinksDAO extends BasicDAO<Links, String> implements BaseDAO<Links> {

    private static final Logger log = Logger.getLogger(LinksDAO.class.getName());
    protected Morphia morphia;

    protected LinksDAO(MongoClient mongoClient, Morphia morphia, String dbName) {
        super(mongoClient, morphia, dbName);
        this.morphia = morphia;
    }
    @Override
    public List<String> add(List<Links> entityList) throws DBException {
        return null;
    }

    @Override
    public long remove(List<String> entityIdsList) throws DBException {
        return 0;
    }

    @Override
    public void update(List<Links> entityList) throws DBException {

    }

    @Override
    public List<Links> fetchById(List<String> entityIdsList, Integer limit)  {
        List<Integer> objectIds = new ArrayList<>();
        Query<Links> query =  null;

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
        QueryResults<Links> results = this.find(query);
        List<Links> linksList = null;
        try {
            linksList  = results.asList();
        } catch (NumberFormatException nfe) {
            // ignore
        }
        return linksList;
    }

    @Override
    public List<Links> fetch(String jsonQueryString) throws DBException {
        return null;
    }
}
