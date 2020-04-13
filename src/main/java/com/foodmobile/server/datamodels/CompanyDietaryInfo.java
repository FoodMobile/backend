package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class CompanyDietaryInfo extends BaseDataModel {
    public static CompanyDietaryInfo create(boolean hasGMO, boolean hasGF, boolean onlyGMO, boolean onlyGF, boolean usesNuts, boolean veganOptions, boolean onlyVegan, String genreGuid, String companyGuid) {
        var dietaryInfo = new CompanyDietaryInfo();
        dietaryInfo.hasGMO = hasGMO;
        dietaryInfo.hasGF = hasGF;
        dietaryInfo.onlyGMO = onlyGMO;
        dietaryInfo.onlyGF = onlyGF;
        dietaryInfo.usesNuts = usesNuts;
        dietaryInfo.veganOptions = veganOptions;
        dietaryInfo.onlyVegan = onlyVegan;
        dietaryInfo.genreGuid = genreGuid;
        dietaryInfo.companyGuid = companyGuid;
        return dietaryInfo;
    }


    public boolean hasGMO;

    public boolean hasGF;

    public boolean onlyGMO;

    public boolean onlyGF;

    public boolean usesNuts;

    public boolean veganOptions;

    public boolean onlyVegan;

    public String genreGuid;

    public String companyGuid;
}