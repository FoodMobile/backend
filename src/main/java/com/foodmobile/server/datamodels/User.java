package com.foodmobile.server.datamodels;

import com.foodmobile.databaselib.annotations.DBId;
import com.foodmobile.databaselib.models.Entity;
import com.foodmobile.server.util.PasswordHasher;
import org.bson.types.ObjectId;

public class User extends Entity {
    public static User create(String name, String username, String password, String email) {
        var user = new User();
        user.name = name;
        user.username = username;
        user.email = email;
        user.setPassword(password);
        return user;
    }

    @DBId
    public ObjectId id;

    public String name;

    public String username;

    public String passwordHash;

    public String email;

    public void setPassword(String password) {
        passwordHash = PasswordHasher.hash(password.trim());
    }

    public boolean passwordMatches(String password) {
        return passwordHash.trim().equals(PasswordHasher.hash(password.trim()));
    }
}
