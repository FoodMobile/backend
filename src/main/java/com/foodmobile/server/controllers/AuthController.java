package com.foodmobile.server.controllers;

import com.foodmobile.server.datamodels.DataModelResponse;
import com.foodmobile.server.datamodels.LoginResponse;
import com.foodmobile.server.datamodels.SimpleStatusResponse;
import com.foodmobile.server.datamodels.User;
import com.foodmobile.server.datapersistence.DAO;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {
    @GetMapping(path = "/login", produces="application/json")
    public LoginResponse login(@RequestParam String username, @RequestParam String password) {
        try (var dao = new DAO()) {
            if (dao.validLoginCreds(username, password)) {
                return LoginResponse.success(dao.createSession(username));
            } else {
                return LoginResponse.failure("Invalid username or password");
            }
        } catch (Exception ex) {
            return LoginResponse.failure(ex);
        }
    }

    @GetMapping(path = "/auth/userinfo", produces = "application/json")
    public DataModelResponse<User> userInfo(@RequestParam String username) {
        try (var dao = new DAO()) {
            var optUser = dao.getUserByUsername(username);
            if (optUser.isEmpty()) {
                return DataModelResponse.failure("No such user");
            }
            optUser.get().passwordHash = "";
            return DataModelResponse.success(optUser.get());
        } catch (Exception ex) {
            return DataModelResponse.failure(ex.getMessage());
        }
    }

    @GetMapping(path="/resetpassword", produces="application/json")
    public SimpleStatusResponse resetPassword(@RequestParam String username, @RequestParam String oldPassword, @RequestParam String newPassword) {
        try (var dao = new DAO()) {
            dao.resetPassword(username, oldPassword, newPassword);
            return SimpleStatusResponse.success();
        } catch (Exception ex) {
            return SimpleStatusResponse.failure(ex.getMessage());
        }
    }

    @GetMapping(path="/register/normal", produces="application/json")
    public SimpleStatusResponse registerNormal(@RequestParam String name, @RequestParam String username, @RequestParam String password, @RequestParam String email) {
        try (var dao = new DAO()) {
            if (dao.getUserByUsername(username).isPresent()) {
                return SimpleStatusResponse.failure("User already exists");
            } else if (password.length() <= 4) {
                return SimpleStatusResponse.failure("Password too short");
            } else if (!email.matches(".+?@.+?\\..+")) {
                return SimpleStatusResponse.failure("Invalid email address");
            }
            dao.register(name, username, password, email);
            return SimpleStatusResponse.success();
        } catch (Exception ex) {
            return SimpleStatusResponse.failure(ex.getMessage());
        }
    }
}
