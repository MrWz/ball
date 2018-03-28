package com.xaut.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * MD5带盐加密算法
 */
public class Md5SaltUtil {

    private static Logger logger = LoggerFactory.getLogger(Md5SaltUtil.class);

    private static final String[] GOAL = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

    /**
     * 根据对象和盐值获取MD5
     *
     * @param object 明文
     * @param salt   盐值
     * @return
     */
    public static String getMD5(String object, String salt) {
        String result = "";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            result = byteArrayToString(digest.digest(addSalt(object, salt).getBytes()));
        } catch (NoSuchAlgorithmException e) {
            logger.error("MD5加密出现错误");
        }
        return result.toUpperCase();
    }

    /**
     * 在加密对象后加盐
     *
     * @param object 明文
     * @param salt   盐值
     * @return
     */
    private static String addSalt(String object, String salt) {
        if (object == null) {
            object = "";
        }
        if (salt == null || "".equals(salt)) {
            return object;
        } else {
            return object + "{" + salt + "0005F386F29DF845230FEA213AA2BB3C" + "}";
        }
    }

    private static String byteArrayToString(byte[] ss) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < ss.length; i++) {
            result.append(byteToString(ss[i]));
        }
        return result.toString();
    }

    private static String byteToString(byte ss) {
        int temp;
        temp = ss < 0 ? ss + 256 : ss;

        return GOAL[temp / 16] + GOAL[temp % 16];
    }

}