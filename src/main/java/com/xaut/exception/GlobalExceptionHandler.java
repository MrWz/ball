package com.xaut.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Author ： Wangzhe
 * Date :  on 2017/12/15
 * Description : 全局异常处理
 * Version : 0.1
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger LOGGER = LogManager.getFormatterLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public ErrorMessage errorHandler(Exception ex) {
        ErrorMessage errorMessage = new ErrorMessage(ex);
        LOGGER.catching(ex);
        return errorMessage;
    }
}
