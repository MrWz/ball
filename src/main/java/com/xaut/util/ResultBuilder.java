package com.xaut.util;


import com.xaut.constant.Constant;
import com.xaut.exception.ErrorsEnum;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Author ： Wangzhe
 * Date :  on 2017/12/15
 * Description : 返回结果工具类
 * Version : 0.1
 */
public class ResultBuilder {

    private int code;
    private String message;
    private Map<String, Object> dataMap = new HashMap<>();

    public static ResultBuilder create() {
        return new ResultBuilder();
    }

    public ResultBuilder code(int code) {
        this.code = code;
        return this;
    }

    public ResultBuilder ok() {
        this.code = ErrorsEnum.SUCCESS.getCode();
        this.message = ErrorsEnum.SUCCESS.getMessage();
        dataMap.put(Constant.DATA, Collections.emptyList());
        return this;
    }

    public ResultBuilder error(ErrorsEnum error) {
        this.code = error.getCode();
        this.message = error.getMessage();
        return this;
    }

    public ResultBuilder error(ErrorsEnum error, Object content) {
        this.code = error.getCode();
        this.message = error.getMessage();
        dataMap.put(Constant.DATA, content);
        return this;
    }

    public ResultBuilder message(String message) {
        this.message = message;
        return this;
    }

    public ResultBuilder data(String field, Object content) {
        dataMap.put(field, content);
        return this;
    }

    public ResultBuilder data(Object content) {
        this.code = ErrorsEnum.SUCCESS.getCode();
        this.message = ErrorsEnum.SUCCESS.getMessage();
        dataMap.put(Constant.DATA, content);
        return this;
    }

    public Map build() {
        Map map = new HashMap();
        map.put(Constant.CODE, code);
        map.putAll(dataMap);
        if (message != null) {
            map.put(Constant.MESSAGE, message);
        }
        return map;
    }
//    public static Map requestNotValid(BindingResult result) {
//        return create().error(ErrorsEnum.REQUEST_PARAMS_NOT_VALID)
//                .message(ValidatorUtil.buildErrorMessage(result))
//                .build();
//    }
}

