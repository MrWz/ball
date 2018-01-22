package com.xaut.util;

import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;

/**
 * Author ： Wangzhe
 * Date :  on 2017/12/15
 * Description :  签名工具类
 * Version : 0.1
 */
@Slf4j
public class SignUtil {

    private SignUtil() {
        throw new UnsupportedOperationException();
    }

    public static boolean validSign(@NotNull String parStr, @NotNull String signStr, @NotNull String appSecret) {
        String str = parStr.toUpperCase();
        String sign = SHA256Util.encodeHmacSHA256(str, appSecret);
        if (!signStr.equals(sign)) {
            log.info(String.format("请求入参拼接: %s, 上送sign: %s , 生成sign:%s ,使用的appSecret:%s", str, signStr, sign, appSecret));
            return false;
        }
        return true;
    }
}
