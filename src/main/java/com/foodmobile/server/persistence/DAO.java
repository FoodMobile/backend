package com.foodmobile.server.persistence;

import com.foodmobile.databaselib.DatabaseAdapter;
import com.foodmobile.databaselib.adapters.MongoQuery;
import com.foodmobile.server.persistence.models.User;
import com.mongodb.client.model.Filters;
import org.bson.types.ObjectId;

import java.util.Optional;

public class DAO {
    public Optional<User> userById(ObjectId id) throws Exception {
        MongoQuery query = DatabaseAdapter.produceMongoAdapter().queryFactory();
        query.setFilter(Filters.eq("_id",id));
        return DatabaseAdapter.produceMongoAdapter().read(query,User.class).stream().findFirst();
    }

}
