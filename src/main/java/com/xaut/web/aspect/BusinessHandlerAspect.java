package com.xaut.web.aspect;

import com.xaut.dto.TokenModel;
import com.xaut.entity.UserInfo;
import com.xaut.manager.TokenManager;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author : wangzhe
 * Date : on 2017/1/9
 * Description :
 * Version :
 */
@Slf4j
@Aspect
@Component
public class BusinessHandlerAspect {

    @Autowired
    TokenManager tokenManager;

    @Around("@annotation(com.xaut.web.annotation.Authorization)")
    public Object validate(ProceedingJoinPoint joinPoint) throws Throwable {
        System.out.println("aaaaaaaaaaaaaaaaaaaaaa");

        Signature sig = joinPoint.getSignature();
        if (!(sig instanceof MethodSignature)) {
            throw new IllegalArgumentException("切入点非方法");
        }

        Object[] args = joinPoint.getArgs();
        /**
         * todo
         */
        Boolean isOnline = tokenManager.checkToken(new TokenModel());
        System.out.println(((UserInfo)args[0]).getName());

        if(isOnline){
            return joinPoint.proceed();
        }else {
            System.out.println("noOnline");
            return null;
        }
    }
}
