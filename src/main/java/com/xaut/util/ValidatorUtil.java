package com.xaut.util;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.List;

/**
 * Author ： Wangzhe
 * Date :  on 2017/12/15
 * Description : 参数校验器工具类
 * Version : 1.0
 */
public class ValidatorUtil {

    private ValidatorUtil() {
        throw new UnsupportedOperationException();
    }

    /**
     * 获取验证结果中的错误提示信息
     * @param result : 验证结果
     * @return String : 提示信息字符串
     */
    public static String buildErrorMessage(BindingResult result) {
        StringBuilder message = new StringBuilder();
        List<ObjectError> list = result.getAllErrors();
        if (CollectionUtils.isNotEmpty(list)) {
            for (ObjectError elem : list) {
                String defaultMessage = elem.getDefaultMessage();
                if (StringUtils.isNotEmpty(defaultMessage)) {
                    message.append(defaultMessage).append("  ");
                }
            }
        }
        return message.toString();
    }
}
