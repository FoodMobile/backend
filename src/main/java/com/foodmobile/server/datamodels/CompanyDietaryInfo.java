package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;

import org.bson.types.ObjectId;

public class CompanyDietaryInfo {
    /*
    public static CompanyDietaryInfo create(boolean hasGMO, boolean hasGF, boolean onlyGMO, boolean onlyGF, boolean usesNuts, boolean veganOptions, boolean onlyVegan, String genreGuid, )
*/
    public String guid;

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