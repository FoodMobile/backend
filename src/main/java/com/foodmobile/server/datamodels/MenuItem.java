package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class MenuItem extends Entity{
    public String guid;

    public String[] ingredients;

    public String description;

    public String title;

    public boolean glutenFree;

    public boolean noGMOs;

    public boolean cookedNearNuts;

    public boolean containsNuts;

    public boolean cookedNearShellfish;

    public boolean containsShellfish;

    public boolean vegan;

    public int primaryPrice;

    public int fractionalPrice;
}