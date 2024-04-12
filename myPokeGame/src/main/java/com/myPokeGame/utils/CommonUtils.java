package com.myPokeGame.utils;

import org.springframework.util.ObjectUtils;

public class CommonUtils {

    public static <T> T getObjectOrNull( T obj){
        return ObjectUtils.isEmpty(obj)?null:obj;
    }
}
