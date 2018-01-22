package com.xaut.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;

/**
 * Author ： Wangzhe
 * Date :  on 2017/12/15
 * Description : MSHA256工具类
 * Version : 0.1
 */
@Slf4j
public class SHA256Util {


    private SHA256Util() {
        throw new UnsupportedOperationException();
    }

    public static String encodeHmacSHA256(byte[] data, byte[] key) throws Exception {
        SecretKey secretKey = new SecretKeySpec(key, "HmacSHA256");
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        byte[] digest = mac.doFinal(data);
        return new HexBinaryAdapter().marshal(digest);
    }

    @Nullable
    public static String encodeHmacSHA256(@NotNull String data, @NotNull String key) {
        try {
            return encodeHmacSHA256(data.getBytes(), key.getBytes());
        } catch (Exception e) {
            log.error("获取sha256摘要异常:{}", e);
        }
        return null;
    }

}
