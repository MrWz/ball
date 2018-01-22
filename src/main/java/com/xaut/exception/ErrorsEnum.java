package com.xaut.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

/**
 * Author ： Wangzhe
 * Date :  on 2017/12/15
 * Description : 状态码
 * 1- 参数校验相关的错误统一使用40000 ,不同的字段校验描述信息不同即可
 * 2- 41xxx  41开通的错误码,是和鉴权相关的错误码
 * 3- 42xxx
 * ...
 * 9- 49xxx 49开头的错误是调用其他系统返回错误,导致产生的错误
 * 10- 本系统内的未知错误统一使用 50000
 * Version : 0.1
 */
@AllArgsConstructor
@Getter
public enum ErrorsEnum {

    SUCCESS(200, "Success"),  //成功
    ERROR(40000, "Error"), //失败

    SQL_ERROR(70001, "数据库操作失败"),
    REQUEST_PARAMS_NOT_VALID(40000, "Request Params Illegal"),//请求参数非法
    TOKEN_AUTHENTICATION_FAILED(41000, "Token Authentication Failed"),//验证失败且需要重定向到登录页面
    IP_AUTHENTICATION_FAILED(41001, "Ip Authentication Failed"),//ip白名单认证失败
    SIGN_AUTHENTICATION_FAILED(41002, "Sign Authentication Failed"),//签名认证失败
    REQUEST_UNAUTHORIZED(41003, "Request Unauthorized"), //请求未授权
    INTERNAL_SERVER_ERROR(50000, "Server Internal Error"),//服务T器内部错误

    RESOURCE_NOT_EXIT(42000,"The Resource NOT Exit"),
    RESOURCE_IS_EXIT(42002,"The Resource Is Exit");

    private int    code;
    private String message;
    private static Map<Integer, String> map = new HashMap<>();

    public static String getMessage(int code) {
        return map.getOrDefault(code, "No message");
    }

    static {
        for (ErrorsEnum code : ErrorsEnum.values()) {
            map.put(code.getCode(), code.getMessage());
        }
    }
}
