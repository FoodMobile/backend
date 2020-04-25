package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

import java.util.UUID;

public class CompanyDietaryInfo extends BaseDataModel {
    public static CompanyDietaryInfo create(boolean hasGMO, boolean hasGF, boolean onlyGMO, boolean onlyGF, boolean usesNuts, boolean veganOptions, boolean onlyVegan, String genreGuid) {
        var dietaryInfo = new CompanyDietaryInfo();
        dietaryInfo.guid = UUID.randomUUID().toString();
        dietaryInfo.hasGMO = hasGMO;
        dietaryInfo.hasGF = hasGF;
        dietaryInfo.onlyGMO = onlyGMO;
        dietaryInfo.onlyGF = onlyGF;
        dietaryInfo.usesNuts = usesNuts;
        dietaryInfo.veganOptions = veganOptions;
        dietaryInfo.onlyVegan = onlyVegan;
        dietaryInfo.genreGuid = genreGuid;
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
}
