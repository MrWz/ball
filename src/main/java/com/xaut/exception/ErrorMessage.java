package com.xaut.exception;

import com.alibaba.fastjson.annotation.JSONField;

import java.sql.SQLException;


/**
 * Author ： Wangzhe
 * Date :  on 2017/12/15
 * Description : 返回错误的信息
 * Version : 0.1
 */
public final class ErrorMessage {

    @JSONField(name = "code")
    public final int code;

    @JSONField(name = "message")
    public final String message;

    public ErrorMessage(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ErrorMessage(Exception e) {
        if (e instanceof BusinessException) {
          this.code = ((BusinessException) e).getCode();
          this.message = e.getMessage();
        } else if (e instanceof SQLException) {
            this.code = ErrorsEnum.SQL_ERROR.getCode();
            this.message = ErrorsEnum.SQL_ERROR.getMessage();
        } else if (e instanceof  IllegalArgumentException) {
            this.code = ErrorsEnum.REQUEST_PARAMS_NOT_VALID.getCode();
            this.message = e.getMessage();
        } else {
            this.code = ErrorsEnum.INTERNAL_SERVER_ERROR.getCode();
            this.message = e.getMessage();
        }
    }
}
