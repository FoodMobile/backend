package com.foodmobile.server.datamodels;

import java.util.List;

public class MultiDataModelResponse<T extends BaseDataModel> {
    public List<T> data = List.of();
    public boolean success;
    public String errorMessage;

    public static <T extends BaseDataModel> MultiDataModelResponse<T> success(List<T> data) {
        var response = new MultiDataModelResponse<T>();
        response.data = data;
        response.success = true;
        response.errorMessage = "";
        return response;
    }

    public static <T extends BaseDataModel> MultiDataModelResponse<T> failure(String errorMessage) {
        var response = new MultiDataModelResponse<T>();
        response.success = false;
        response.errorMessage = errorMessage;
        return response;
    }
}
