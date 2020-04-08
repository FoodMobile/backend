package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;

import org.bson.types.ObjectId;

public class Truck {
    @DBId
    public ObjectId id;

    public Company company;

    public User associatedUser;
}