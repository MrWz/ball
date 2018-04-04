package com.xaut.exception;

/**
 * 系统业务异常
 */
public class BusinessException extends RuntimeException {

    private int code;

    public BusinessException() {}

    public BusinessException(ErrorsEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

}