package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class FoodGenre extends Entity {
    public String guid;

    public String name;
}