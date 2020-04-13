package com.foodmobile.server.datamodels;

import java.util.UUID;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class Truck extends BaseDataModel {
    public static Truck create(String companyGuid, String associatedGuid) {
        var truck = new Truck();
        truck.guid = UUID.randomUUID().toString();
        truck.companyGuid = companyGuid;
        truck.associatedGuid = associatedGuid;
        return truck;
    }

    public String companyGuid;

    public String associatedGuid;
}