package com.foodmobile.server.datamodels;

public class LoginResponse {
    public boolean success;

    public String errorMessage;

    public String token;

    public static LoginResponse success(String token) {
        var response = new LoginResponse();
        response.success = true;
        response.errorMessage = "";
        response.token = token;
        return response;
    }

    public static LoginResponse failure(String errorMessage) {
        var response = new LoginResponse();
        response.success = false;
        response.errorMessage = errorMessage;
        return response;
    }
}
