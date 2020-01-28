package com.foodmobile.server.datamodels;

public class ApiTestObject {
    public static int globalCounter = 0;

    public String message = "This is a test object";

    public int visitCount = ApiTestObject.globalCounter++; 
}