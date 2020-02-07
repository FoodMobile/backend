package com.foodmobile.server.Auth.LoginModels;

import java.util.LinkedHashMap;

public class FacebookPayload{
    public String oauth;
    public FacebookPayload(LinkedHashMap<String,Object> map){
        this.oauth = (String)map.get("oauth");
    }
}
