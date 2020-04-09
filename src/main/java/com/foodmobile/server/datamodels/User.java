package com.foodmobile.server.datamodels;

import java.util.UUID;

import com.foodmobile.server.util.PasswordHasher;
import com.foodmobile.databaselib.models.Entity;
import org.bson.types.ObjectId;

public class User extends Entity {
    public static User create(String name, String username, String password, String email) {
        var user = new User();
        user.guid = UUID.randomUUID().toString();
        user.name = name;
        user.username = username;
        user.email = email;
        user.setPassword(password);
        return user;
    }

    public String guid;

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

    public static boolean usersAreEqual(User a, User b){
        return a.name.equals(b.name) &&
                a.email.equals(b.email) &&
                a.passwordHash.equals(b.passwordHash) &&
                a.guid.equals(b.guid) &&
                a.username.equals(b.username);
    }

}
