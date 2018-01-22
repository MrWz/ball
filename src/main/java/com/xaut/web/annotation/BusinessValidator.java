package com.xaut.web.annotation;

import java.lang.annotation.*;

/**
 * Author : wangzhe
 * Date : on 2018/1/3
 * Description :
 * Version :
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessValidator {

    Class value();

}
