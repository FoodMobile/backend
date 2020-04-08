package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;

import org.bson.types.ObjectId;

public class CompanyFinancial {
    @DBId
    public ObjectId id;

    public String ein;

    public String stateCode;

    public String country;
}