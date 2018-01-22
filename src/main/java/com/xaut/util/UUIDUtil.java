package com.xaut.util;

import java.util.UUID;

/**
 * Author : Wangzhe
 * Date : 2017/11/30
 * Description : uuid产生器
 * version : 1.0
 */
public class UUIDUtil {

    public static String getId(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-","");
    }
}
