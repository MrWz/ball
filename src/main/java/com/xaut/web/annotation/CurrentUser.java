package com.xaut.web.annotation;


import com.xaut.constant.Constant;

import java.lang.annotation.*;

/**
 * Author : wangzhe
 * Description : 获取当前用户的注解
 * Version : 1.0
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CurrentUser {
    String value() default Constant.CURRENT_USER;
}
