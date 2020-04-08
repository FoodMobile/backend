package com.foodmobile.server.datamodels;

public class DataModelResponse<T> {
    public T data;
    public boolean success;
    public String errorMessage;

    public static <T> DataModelResponse<T> success(T t) {
        var response = new DataModelResponse<T>();
        response.success = true;
        response.data = t;
        response.errorMessage = "";
        return response;
    }

    public static <T> DataModelResponse<T> failure(String errorMessage) {
        var response = new DataModelResponse<T>();
        response.data = null;
        response.success = false;
        response.errorMessage = errorMessage;
        return response;
    }
}