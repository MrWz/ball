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
    REQUEST_UNAUTHORIZED(41003, "Request Unauthorized"), //请求未授权
    INTERNAL_SERVER_ERROR(50000, "Server Internal Error"),//服务T器内部错误
    RESOURCE_NOT_EXIT(42000,"The Resource NOT Exit"),
    RESOURCE_IS_EXIT(42002,"The Resource Is Exit"),

    //应该添加具体的业务异常
    EX_10000(10000, "业务异常"),
    EX_10001(10001, "参数异常，不能为空"),
    EX_10002(10002, "Unknown异常"),


    EX_20007(20007, "请求头信息丢失"),

    EX_20008(20008, "用户名仅支持数字和字母"),
    EX_20009(20009, "密码长度大于等于6位，小于等于32"),
    EX_20010(20010, "上传用户头像异常"),
    EX_200101(200101, "该用户不存在，请您检查用户名"),

    EX_20011(20011, "您的比赛类型与场地类型不否"),
    EX_20012(20012, "该时间段场地已经被占用"),
    EX_20013(20013, "开始时间不能晚于结束时间"),
    EX_20014(20014, "您没有权限删除帖子"),

    /**
     * 管理员异常
     */
    EX_30001(30001, "权限异常，请您前往管理员登录界面进行登录");//管理员帐号不能从普通用户登录界面登录



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
