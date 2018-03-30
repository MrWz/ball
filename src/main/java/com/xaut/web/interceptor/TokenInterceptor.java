package com.xaut.web.interceptor;

import com.xaut.constant.Constant;
import com.xaut.constant.HeaderConstant;
import com.xaut.constant.SystemConstant;
import com.xaut.dto.TokenModel;
import com.xaut.dto.UserInfoDto;
import com.xaut.manager.TokenManager;
import com.xaut.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author ： wangzhe
 * Date : 17/11/13
 * Description : token拦截器
 * Version : 0.1
 */
@Slf4j
@Component
public class TokenInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserService userService;
    @Autowired
    private TokenManager tokenManager;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        String token = StringUtils.trimToEmpty(request.getHeader(HeaderConstant.X_AUTH_TOKEN));
        if (StringUtils.isEmpty(token)) {
            log.info("用户鉴权失败,token为空");
            return false;
        }
        /**
         * todo
         */
        if (!tokenManager.checkToken(new TokenModel())) {
            log.info("token错误");
            return false;
        }

        try {
            UserInfoDto userInfo = userService.authForUser(token, SystemConstant.SYSTEM_CODE);
            if (StringUtils.isNotBlank(userInfo.getToken())) {
                userInfo.setToken(token);
                request.setAttribute(Constant.CURRENT_USER, userInfo);
                return true;
            }
        } catch (Exception e) {
            log.info("用户鉴权失败,token验证异常{}", e);
        }
        log.info("用户鉴权失败,返回结果为空");
        return false;
    }
}
