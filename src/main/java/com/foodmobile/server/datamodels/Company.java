package com.foodmobile.server.datamodels;

import java.util.UUID;

import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class Company extends Entity {
    
    public static Company create(String name, String financialInfoGuid) {
        var response = new Company();
        response.guid = UUID.randomUUID().toString();
        response.name = name;
        response.financialInformationGuid = financialInfoGuid;
        return response;
    }

    public String guid;

    public String name;

    public String financialInformationGuid;
}