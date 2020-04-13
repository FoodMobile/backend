package com.foodmobile.server.datamodels;

public class DataModelResponse<T extends BaseDataModel> {
    public T data;
    public boolean success;
    public String errorMessage;

    public static <T extends BaseDataModel> DataModelResponse<T> success(T t) {
        var response = new DataModelResponse<T>();
        response.success = true;
        response.data = t;
        response.errorMessage = "";
        return response;
    }

    public static <T extends BaseDataModel> DataModelResponse<T> failure(String errorMessage) {
        var response = new DataModelResponse<T>();
        response.data = null;
        response.success = false;
        response.errorMessage = errorMessage;
        return response;
    }
}