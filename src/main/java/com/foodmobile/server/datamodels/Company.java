package com.foodmobile.server.datamodels;

import java.util.UUID;

import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class Company extends BaseDataModel {

    public static Company create(String name, String financialInfoGuid, String dietaryInfoGuid, String userGuid) {
        var response = new Company();
        response.guid = UUID.randomUUID().toString();
        response.name = name;
        response.dietaryInformationGuid = dietaryInfoGuid;
        response.financialInformationGuid = financialInfoGuid;
        response.userGuid = userGuid;
        return response;
    }

    public String name;

    public String financialInformationGuid;

    public String dietaryInformationGuid;

    public String userGuid;
}