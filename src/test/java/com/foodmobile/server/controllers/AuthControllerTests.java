package com.foodmobile.server.controllers;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.UUID;

public class AuthControllerTests {
    private final AuthController auth = new AuthController();

    @Test
    public void loginTest() {
        var username = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();
        var email = String.format("%s@gmail.com", UUID.randomUUID().toString());
        var regResponse = auth.registerNormal("Joe Shmoe", username, password, email);
        assertTrue(regResponse.success);
        var response = auth.login(username, password);
        assertTrue(response.success);
    }

    @Test
    public void invalidPasswordTest() {
        var username = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();
        var email = String.format("%s@gmail.com", UUID.randomUUID().toString());
        var regResponse = auth.registerNormal("Joe Shmoe", username, password, email);
        assertTrue(regResponse.success);
        var response = auth.login(username, "wrong");
        assertFalse(response.success);
    }

    @Test
    public void registrationTest() {
        var username = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();
        var email = String.format("%s@gmail.com", UUID.randomUUID().toString());
        var regResponse = auth.registerNormal("Joe shmoe", username, password, email);
        assertTrue(regResponse.success);
    }

    @Test
    public void resetPasswordTest() {
        var username = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();
        var email = String.format("%s@gmail.com", UUID.randomUUID().toString());
        var regResponse = auth.registerNormal("joe shmoe", username, password, email);
        assertTrue(regResponse.success);
        var response = auth.resetPassword(username, password, "test");
        assertTrue(response.success);
    }

    @Test
    public void resetInvalidPasswordTest() {
        var username = UUID.randomUUID().toString();
        var password = UUID.randomUUID().toString();
        var email = String.format("%s@gmail.com", UUID.randomUUID().toString());
        var regResponse = auth.registerNormal("joe shmoe", username, password, email);
        assertTrue(regResponse.success);
        var response = auth.resetPassword(username, "wrong", "test");
        assertFalse(response.success);
    }
}
