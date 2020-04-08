package com.foodmobile.server.util.jwt;

import java.security.SecureRandom;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator.Builder;
import com.auth0.jwt.algorithms.Algorithm;

public class JsonWebTokenBuilder {
    private Algorithm algorithm;
    private Builder token;

    public JsonWebTokenBuilder(Algorithm algorithm, Builder builder) {
        this.algorithm = algorithm;
        this.token = builder;
    }

    public JsonWebTokenBuilder add(String key, String value) {
        this.token = this.token.withClaim(key, value);
        return this;
    }

    public JsonWebTokenBuilder addInt(String key, int value) {
        this.token = this.token.withClaim(key, value);
        return this;
    }
    
    public JsonWebTokenBuilder addBoolean(String key, boolean value) {
        this.token = this.token.withClaim(key, value);
        return this;
    }

    public String build() {
        return this.token.sign(algorithm);
    }
}