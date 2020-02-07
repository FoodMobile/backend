package com.foodmobile.server.auth.loginmodels;

import com.foodmobile.server.exceptions.MissingKeyException;

import java.util.LinkedHashMap;
import java.util.Optional;

public class FacebookPayload{
    public String oauth;
    public FacebookPayload(LinkedHashMap<String,Object> map) throws MissingKeyException {
        this.oauth = Optional.of(
                (String)map.getOrDefault("oauth",null))
                .orElseThrow(() ->new MissingKeyException("oauth")
                );
    }
}
