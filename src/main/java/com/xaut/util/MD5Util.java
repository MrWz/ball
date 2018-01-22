package com.xaut.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.codec.digest.DigestUtils;

import java.security.MessageDigest;

/**
 * Author ： Wangzhe
 * Date :  on 2017/12/15
 * Description : MD5工具类
 * Version : 0.1
 */
@Slf4j
public class MD5Util {
    private static Object md5Lock = new Object();
    private static final MessageDigest md = DigestUtils.getMd5Digest();

    private MD5Util() {
        throw new UnsupportedOperationException();
    }

    public static String encryptMD5(String encTxt) {
        synchronized (md5Lock) {
            return Hex.encodeHexString(md.digest(StringUtils.getBytesUtf8(encTxt)));
        }
    }
}
