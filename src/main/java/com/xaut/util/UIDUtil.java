package com.xaut.util;

import java.util.UUID;

/**
 * Created by cvter on 2017/5/16.
 */
public class UIDUtil {

    public static String getRandomUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
