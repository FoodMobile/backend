package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;

import org.bson.types.ObjectId;

public class CompanyDietaryInfo {
    @DBId
    public ObjectId id;

    public boolean hasGMO;

    public boolean hasGF;

    public boolean onlyGMO;

    public boolean onlyGF;

    public boolean usesNuts;

    public boolean veganOptions;

    public boolean onlyVegan;

    public FoodGenre genre;

    public Company company;
}