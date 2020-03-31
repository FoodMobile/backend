package com.foodmobile.server.persistence.models;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;
import org.bson.Document;
import org.bson.types.ObjectId;

public class User extends Entity {
    @DBId
    private ObjectId _id;
    private String name;
    private String email;
    private String phash;
    private String phone;
    private String userType;


}
