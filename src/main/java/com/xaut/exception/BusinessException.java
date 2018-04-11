package com.xaut.exception;

/**
 * 系统业务异常
 */
public class BusinessException extends RuntimeException {

    private int statusCode;

    public BusinessException() {}

    public BusinessException(ErrorsEnum resultEnum) {
        super(resultEnum.getMessage());
        this.statusCode = resultEnum.getCode();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.statusCode = code;
    }

    public int getCode() {
        return statusCode;
    }

    public void setCode(int code) {
        this.statusCode = code;
    }

}