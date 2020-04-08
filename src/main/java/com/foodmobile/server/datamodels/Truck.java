package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class Truck extends Entity {
    public String guid;

    public Company company;

    public User associatedUser;
}