package com.foodmobile.server.datapersistence;

import com.foodmobile.databaselib.DatabaseAdapter;
import com.foodmobile.databaselib.adapters.ConnectionInfo;
import com.foodmobile.databaselib.adapters.MongoQuery;
import com.foodmobile.databaselib.models.Entity;
import com.foodmobile.server.auth.AuthenticationException;
import com.foodmobile.server.datamodels.BaseDataModel;
import com.foodmobile.server.datamodels.Truck;
import com.foodmobile.server.datamodels.User;
import com.foodmobile.server.util.jwt.JsonWebToken;

import java.io.Closeable;
import java.util.List;
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
     * @throws AuthenticationException If the provided current password is incorrect.
     * @throws PersistenceException if there was an issue communicating with the database or (potentially?) the user does not exist.
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
            persistence.update("User", user.get());
        } else {
            throw new PersistenceException();
        }
    }

    public void register(String name, String username, String password, String email) throws Exception {
        var user = User.create(name, username, password, email);
        persistence.create("User", user);
    }

    private Optional<User> userByUsername(String username) throws PersistenceException {
        return persistence.readWhereEqual("User", User.class, "username", username);
    }

    public <T extends BaseDataModel> void update(T t) throws PersistenceException {
        deleteByGuid(t.getClass(), t.guid);
        create(t);
    }

    public <T extends Entity> void deleteByGuid(Class<T> tClass, String guid) throws PersistenceException {
        deleteWhereEqual(tClass, "guid", guid);
    } 

    public <T extends Entity, V> void deleteWhereEqual(Class<T> tClass, String key, V value) throws PersistenceException {
        var className = tClass.getSimpleName();
        persistence.deleteWhereEqual(className, tClass, key, value);
    }

    public Optional<Truck> getTruckByToken(String token) throws PersistenceException {
        var username = JsonWebToken.verify(token).get("username");
        var userOpt = userByUsername(username);
        if (!userOpt.isPresent()) {
            throw new PersistenceException("No such user");
        }
        return getTruckByUserId(userOpt.get().guid);
    }

    public Optional<Truck> getTruckByUserId(String userGuid) throws PersistenceException {
        return byKeyValue(Truck.class, "associatedGuid", userGuid);
    }

    public <T extends Entity, V> Optional<T> byKeyValue(Class<T> tClass, String key, V value) throws PersistenceException {
        var className = tClass.getSimpleName();
        return (Optional<T>)persistence.readWhereEqual(className, tClass, key, value);
    }

    public <T extends Entity, V> List<T> allByKeyValue(Class<T> tClass, String key, V value) throws PersistenceException {
        var className = tClass.getSimpleName();
        return persistence.readAllWhereEqual(className, tClass, key, value);
    }

    public <T extends Entity> Optional<T> byGuid(String guid, Class<T> tClass) throws PersistenceException {
        var className = tClass.getSimpleName();
        return (Optional<T>)persistence.readWhereEqual(className, tClass, "guid", guid);
    }

    public <T extends Entity> void create(T t) throws PersistenceException {
        persistence.create(t.getClass().getSimpleName(), t);
    }
    public Optional<User> getUserByUsername(String username) throws PersistenceException{
        return this.userByUsername(username);
    }
}
