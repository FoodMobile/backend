package com.foodmobile.server.util.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;

public class JsonWebTokenReader {
    private DecodedJWT jwt;

    public JsonWebTokenReader(DecodedJWT jwt) {
        this.jwt = jwt;
    }

    public String get(String key) {
        return this.jwt.getClaim(key).asString();
    }

    public int getInt(String key) {
        return this.jwt.getClaim(key).asInt();
    }

    public boolean getBoolean(String key) {
        return this.jwt.getClaim(key).asBoolean();
    }
}