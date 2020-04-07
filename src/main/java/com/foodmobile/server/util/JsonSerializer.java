package com.foodmobile.server.util;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Optional;

public class JsonSerializer {
    public static <T> Optional<T> deserialize(String payload, Class<T> tClass){
        ObjectMapper mapper = new ObjectMapper();
        try {
            return Optional.of(mapper.readValue(payload, tClass));
        }catch (Exception e){
            return Optional.empty();
        }
    }

    public static Optional<String> serialize(Object o){
        try{
        return Optional.of((new ObjectMapper()).writeValueAsString(o));
        }catch(Exception ex){
            ex.printStackTrace();
            return Optional.empty();
        }
    }
}
