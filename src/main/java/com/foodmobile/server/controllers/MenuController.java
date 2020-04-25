package com.foodmobile.server.controllers;

import com.foodmobile.server.datamodels.DataModelResponse;
import com.foodmobile.server.datamodels.MenuItem;
import com.foodmobile.server.datamodels.SimpleStatusResponse;
import com.foodmobile.server.datapersistence.DAO;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.util.Arrays;

@RestController
public class MenuController {
    @PostMapping(path="/menu/createitem", produces="application/json")
    public DataModelResponse<MenuItem> createItem(
            @RequestParam String ingredientsList, 
            @RequestParam String description,
            @RequestParam String title,
            @RequestParam boolean glutenFree,
            @RequestParam boolean noGMOs, 
            @RequestParam boolean cookedNearNuts,
            @RequestParam boolean cookedNearShellfish,
            @RequestParam boolean containsShellfish,
            @RequestParam boolean vegan,
            @RequestParam int primaryPrice,
            @RequestParam int fractionalPrice,
            @RequestParam String businessGuid) {
        var menuItem = MenuItem.create(
            ingredientsList.split(","),
            description,
            title,
            glutenFree,
            noGMOs,
            cookedNearNuts,
            cookedNearShellfish,
            containsShellfish,
            vegan,
            primaryPrice,
            fractionalPrice,
            businessGuid
        );
        try (var dao = new DAO()) {
            dao.create(menuItem);
            return DataModelResponse.success(menuItem);
        } catch (Exception ex) {
            try {
                var byteArr = new ByteArrayOutputStream();
                var printWriter = new PrintWriter(byteArr);
                ex.printStackTrace(printWriter);
                printWriter.flush();
                return DataModelResponse.failure("Err:" + new String(byteArr.toByteArray()));
            } catch (Exception ex2) {
                return null;
            }
        }
    }

    @PostMapping(path="/menu/deleteitem", produces="application/json")
    public SimpleStatusResponse deleteItem(@RequestParam String guid) {
        try (var dao = new DAO()) {
            dao.deleteByGuid(MenuItem.class, guid);
            return SimpleStatusResponse.success();
        } catch (Exception ex) {
            return SimpleStatusResponse.failure(ex.getMessage());
        }
    }

    @PostMapping(path="/menu/updateitem", produces="application/json")
    public DataModelResponse<MenuItem> updateItem(
            @RequestParam String guid,
            @RequestParam String ingredientsList, 
            @RequestParam String description,
            @RequestParam String title,
            @RequestParam boolean glutenFree,
            @RequestParam boolean noGMOs, 
            @RequestParam boolean cookedNearNuts,
            @RequestParam boolean cookedNearShellfish,
            @RequestParam boolean containsShellfish,
            @RequestParam boolean vegan,
            @RequestParam int primaryPrice,
            @RequestParam int fractionalPrice,
            @RequestParam String businessGuid) {
        try (var dao = new DAO()) {
            var menuItemOpt = dao.byGuid(guid, MenuItem.class);
            if (menuItemOpt.isEmpty()) {
                return DataModelResponse.failure("No such MenuItem");
            }
            var menuItem = menuItemOpt.get();
            menuItem.ingredients = Arrays.asList(ingredientsList.split(","));
            menuItem.description = description;
            menuItem.title = title;
            menuItem.glutenFree = glutenFree;
            menuItem.noGMOs = noGMOs;
            menuItem.cookedNearNuts = cookedNearNuts;
            menuItem.cookedNearShellfish = cookedNearShellfish;
            menuItem.vegan = vegan;
            menuItem.primaryPrice = primaryPrice;
            menuItem.fractionalPrice = fractionalPrice;
            menuItem.businessGuid = businessGuid;
            dao.update(menuItem);
            return DataModelResponse.success(menuItem);
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }
}
