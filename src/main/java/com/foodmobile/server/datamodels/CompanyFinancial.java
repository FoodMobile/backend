package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;
import java.util.UUID;

public class CompanyFinancial extends BaseDataModel {

    public static CompanyFinancial create(String ein, String stateCode, String country) {
        var companyFin = new CompanyFinancial();
        companyFin.guid = UUID.randomUUID().toString();
        companyFin.ein = ein;
        companyFin.stateCode = stateCode;
        companyFin.country = country;
        return companyFin;
    }

    public String ein;

    public String stateCode;

    public String country;
}
