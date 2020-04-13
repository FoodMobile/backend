package com.foodmobile.server.datamodels;

import java.util.UUID;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class FoodGenre extends BaseDataModel {
    public static FoodGenre create(String name) {
        var response = new FoodGenre();
        response.guid = UUID.randomUUID().toString();
        response.name = name;
        return response;
    }

    public String name;
}