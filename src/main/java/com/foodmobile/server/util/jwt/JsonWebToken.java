package com.foodmobile.server.util.jwt;

import java.security.SecureRandom;
import java.util.Date;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;

public class JsonWebToken {
    private static final String ISSUER = "FoodMobilePrimaryAPI";

    private static Algorithm signingAlgorithm;

    private static JWTVerifier verifier;

    static {
        final var rand = new SecureRandom();
        var secret = new byte[1024];
        rand.nextBytes(secret);
        signingAlgorithm = Algorithm.HMAC512(secret);
        verifier = JWT.require(signingAlgorithm)
            .withIssuer(ISSUER).build();
    }

    public static JsonWebTokenBuilder create() {
        var builder = JWT.create()
            .withIssuer(ISSUER)
            .withIssuedAt(new Date());
        return new JsonWebTokenBuilder(signingAlgorithm, builder);
    }

    public static JsonWebTokenReader verify(String token) throws JWTVerificationException {
        return new JsonWebTokenReader(verifier.verify(token));
    }
}