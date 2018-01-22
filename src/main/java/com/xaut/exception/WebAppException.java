package com.xaut.exception;

import lombok.Getter;
import lombok.Setter;

/**
 * Author ： Wangzhe
 * Date :  on 2017/12/15
 * Description : 应用异常
 * Version : 0.1
 */
@Getter
@Setter
public class WebAppException extends RuntimeException {

    private final int errorCode;

    public WebAppException(ErrorsEnum error) {
        super(error.getMessage());
        this.errorCode = error.getCode();
    }

    public WebAppException(int code, String msg) {
        super(msg);
        errorCode = code;
    }
}
