package com.foodmobile.server.datamodels;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;

import org.bson.types.ObjectId;

public class MenuItem extends BaseDataModel {
    public static MenuItem create(
            String[] ingredients, String description, String title, 
            boolean glutenFree, boolean noGMOs, boolean cookedNearNuts,
            boolean cookedNearShellfish, boolean containsShellfish,
            boolean vegan, int primaryPrice, int fractionalPrice) {
        var item = new MenuItem();
        item.guid = UUID.randomUUID().toString();
        item.ingredients = Arrays.asList(ingredients);
        item.description = description;
        item.title = title;
        item.glutenFree = glutenFree;
        item.noGMOs = noGMOs;
        item.cookedNearNuts = cookedNearNuts;
        item.cookedNearShellfish = cookedNearShellfish;
        item.containsShellfish = containsShellfish;
        item.vegan = vegan;
        item.primaryPrice = primaryPrice;
        item.fractionalPrice = fractionalPrice;
        return item;
    }

    public List<String> ingredients;

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