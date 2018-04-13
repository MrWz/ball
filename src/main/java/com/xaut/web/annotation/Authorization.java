package com.xaut.web.annotation;

import java.lang.annotation.*;
/**
 * Author : wangzhe
 * Description : 在Controller的方法上使用此注解，该方法在映射时会对用户进行身份验证，验证失败返回401错误
 * Version : 1.0
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Authorization {

}
