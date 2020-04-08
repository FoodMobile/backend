package com.foodmobile.server.datamodels;

import java.io.ByteArrayOutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;

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

    public static LoginResponse failure(Exception ex) {
        try {
            var boa = new ByteArrayOutputStream();
            var writer = new PrintWriter(boa);
            ex.printStackTrace(writer);
            writer.flush();
            var message = new String(boa.toByteArray(), Charset.defaultCharset());
            return failure(message);
        } catch (Exception f) {
            return null;
        }
    }
}
