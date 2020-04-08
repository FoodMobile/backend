package com.foodmobile.server.datapersistence;

import com.foodmobile.databaselib.DatabaseAdapter;
import com.foodmobile.databaselib.adapters.ConnectionInfo;
import com.foodmobile.databaselib.adapters.MongoQuery;
import com.foodmobile.server.auth.AuthenticationException;
import com.foodmobile.server.datamodels.User;
import com.foodmobile.server.util.jwt.JsonWebToken;

import java.io.Closeable;
import java.util.Optional;

public class DAO implements Closeable {
    private final PersistenceWrapper persistence;

    public DAO() throws PersistenceException {
        persistence = PersistenceWrapper.getInstance();
    }

    public void close() {

    }

    public String createSession(String username) throws Exception {
        return JsonWebToken.create().add("username", username).build();
    }

    public boolean validLoginCreds(String username, String password) throws PersistenceException {
        var user = userByUsername(username);
        return user.isPresent() && user.get().passwordMatches(password);
    }

    /**
     * Resets a password from an old password. To reset an unknown password use emailResetPassword
     * @param username The username that needs their password reset
     * @param oldPassword The old password for the user
     * @param newPassword The new password for the user
     * @throws Exception if there was an issue communicating with the database or (potentially?) the user does not exist.
     */
    public void resetPassword(String username, String oldPassword, String newPassword) throws AuthenticationException, PersistenceException {
        if (validLoginCreds(username, oldPassword)) {
            updatePassword(username, newPassword);
        } else {
            throw AuthenticationException.invalidCredentials();
        }
    }

    public void updatePassword(String username, String password) throws PersistenceException {
        var user = userByUsername(username);
        if (user.isPresent()) {
            user.get().setPassword(password);
            persistence.update("users", user.get());
        } else {
            throw new PersistenceException();
        }
    }

    public void register(String name, String username, String password, String email) throws Exception {
        var user = User.create(name, username, password, email);
        persistence.create("users", user);
    }

    private Optional<User> userByUsername(String username) throws PersistenceException {
        return persistence.readWhereEqual("users", User.class, "username", username);
    }
}
