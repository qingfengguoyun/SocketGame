package com.myPokeGame.utils;

import cn.hutool.core.lang.UUID;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.MD5;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.ObjectUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

public class CommonUtils {

    public static <T> T getObjectOrNull( T obj){
        return ObjectUtils.isEmpty(obj)?null:obj;
    }

    /**
     * 计算文件MD5
     * @param inputStream
     * @return
     */
    public static String calcMd5(InputStream inputStream){
        try {
            return DigestUtils.md5Hex(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 获取随机uuid
     * @return
     */
    public static String getRandomUuid(){
        return IdUtil.randomUUID();
    }

    public static String getSuffix(String str){
        int index = str.lastIndexOf(".");
        return index==-1?"":str.substring(index+1);
    }
}
