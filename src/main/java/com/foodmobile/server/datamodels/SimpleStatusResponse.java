package com.foodmobile.server.datamodels;

public class SimpleStatusResponse {
    public boolean success;

    public String errorMessage;

    public static SimpleStatusResponse success() {
        var response = new SimpleStatusResponse();
        response.success = true;
        response.errorMessage = "";
        return response;
    }

    public static SimpleStatusResponse failure(String errorMessage) {
        var response = new SimpleStatusResponse();
        response.success = false;
        response.errorMessage = errorMessage;
        return response;
    }
}
