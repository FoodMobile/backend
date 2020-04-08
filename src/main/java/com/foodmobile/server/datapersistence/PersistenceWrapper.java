package com.foodmobile.server.datapersistence;

import com.foodmobile.databaselib.DatabaseAdapter;
import com.foodmobile.databaselib.adapters.ConnectionInfo;
import com.foodmobile.databaselib.adapters.MongoQuery;
import com.foodmobile.databaselib.models.Entity;
import com.mongodb.client.model.Filters;

import javax.xml.crypto.Data;

import java.net.URLEncoder;
import java.util.List;
import java.util.Optional;

public class PersistenceWrapper {
    private static PersistenceWrapper instance = null;

    public static PersistenceWrapper getInstance() throws PersistenceException {
        if (instance == null) {
            instance = new PersistenceWrapper();
        }
        return instance;
    }

    public PersistenceWrapper() throws PersistenceException {
        try { 
            DatabaseAdapter.connectMongo("mongodb+srv://admin:Testing1234!@cluster0-6th5l.mongodb.net/test", "test");
        } catch (Exception ex) {
            throw new PersistenceException("Failed to initialize", ex);
        }
    }

    public void create(String collection, Entity entity) throws PersistenceException {
        try {
            MongoQuery query = DatabaseAdapter.produceMongoAdapter().queryFactory();
            query.setCollection(collection);
            DatabaseAdapter.produceMongoAdapter().create(query, entity);
        } catch (Exception ex) {
            throw new PersistenceException("Failed to create", ex);
        }
    }

    public <T extends Entity, V> Optional<T> readWhereEqual(String collection, Class<T> tClass, String key, V value) throws PersistenceException {
        try {
            MongoQuery query = DatabaseAdapter.produceMongoAdapter().queryFactory();
            query.setCollection(collection);
            query.setFilter(Filters.eq(key, value));
            return DatabaseAdapter.produceMongoAdapter().readOne(query, tClass);
        } catch (Exception ex) {
            throw new PersistenceException("Failed to read single: " + ex.getMessage(), ex);
        }
    }

    public <T extends Entity> List<T> readAll(String collection, Class<T> tClass) throws PersistenceException {
        try {
            MongoQuery query = DatabaseAdapter.produceMongoAdapter().queryFactory();
            query.setCollection(collection);
            return DatabaseAdapter.produceMongoAdapter().read(query, tClass);
        } catch (Exception ex) {
            throw new PersistenceException("Failed to read collection", ex);
        }
    }

    public <T extends Entity> void updateAll(String collection, List<T> entities) throws PersistenceException {
        try {
            MongoQuery query = DatabaseAdapter.produceMongoAdapter().queryFactory();
            query.setCollection(collection);
            DatabaseAdapter.produceMongoAdapter().update(query, entities);
        } catch (Exception ex) {
            throw new PersistenceException("Failed to update entity", ex);
        }
    }

    public <T extends Entity> void update(String collection, T entity) throws PersistenceException {
        try {
            MongoQuery query = DatabaseAdapter.produceMongoAdapter().queryFactory();
            query.setCollection(collection);
            DatabaseAdapter.produceMongoAdapter().update(query, entity);
        } catch (Exception ex) {
            throw new PersistenceException("Failed to update entity", ex);
        }
    }
}
