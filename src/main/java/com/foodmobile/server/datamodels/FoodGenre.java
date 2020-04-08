package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;

import org.bson.types.ObjectId;

public class FoodGenre {
    @DBId
    public ObjectId id;

    public String name;
}