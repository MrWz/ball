package com.xaut.web.annotation;


import java.lang.annotation.*;

/**
 * Author : wangzhe
 * Date : on 2018/1/4
 * Description :
 * Version :
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface BusinessType {

    int value();

    boolean withBTW();
}
