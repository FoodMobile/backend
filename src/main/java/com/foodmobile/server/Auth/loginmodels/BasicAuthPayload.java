package com.foodmobile.server.auth.loginmodels;

import com.foodmobile.server.exceptions.MissingKeyException;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.LinkedHashMap;
import java.util.Optional;

public class BasicAuthPayload {
    private String username;
    private String password;
    public BasicAuthPayload(LinkedHashMap<String,Object> arg) throws MissingKeyException {
        this.username = Optional.of(
                (String)arg.getOrDefault("username",null)
        ).orElseThrow(()->new MissingKeyException("username"));
        this.password = DigestUtils.sha256Hex(
                Optional.of((String)arg.get("password")).orElseThrow(()->new MissingKeyException("password"))
        );
    }
}
