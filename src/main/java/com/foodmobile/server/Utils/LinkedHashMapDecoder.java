package com.foodmobile.server.Utils;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedHashMap;

public class LinkedHashMapDecoder{
    public static <T> T decode(LinkedHashMap<String,Object> map, Class<T> tClass) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        return tClass.getConstructor(LinkedHashMap.class).newInstance(map);
    }
}