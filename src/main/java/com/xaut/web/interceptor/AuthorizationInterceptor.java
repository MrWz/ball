package com.xaut.web.interceptor;

import com.xaut.constant.Constant;
import com.xaut.constant.HeaderConstant;
import com.xaut.dto.TokenModel;
import com.xaut.exception.BusinessException;
import com.xaut.exception.ErrorsEnum;
import com.xaut.manager.TokenManager;
import com.xaut.util.ResponseUtil;
import com.xaut.web.annotation.Authorization;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Method;

/**
 * Author ： wangzhe
 * Description : 自定义拦截器，对请求进行身份验证
 * Version : 0.1
 */

public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager tokenManager;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) throws UnsupportedEncodingException {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 从 header的cookie中得到 token
        String authentication = null;

//        String authentication = request.getHeader((HeaderConstant.X_AUTH_TOKEN));//todo

        request.setCharacterEncoding("utf-8");
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie c : cookies) {
                if (StringUtils.equals(HeaderConstant.X_AUTH_TOKEN, c.getName())) {
                    authentication = c.getValue();
                }
            }
        }
        TokenModel model = tokenManager.getToken(authentication);
        /**
         * todo
         */
        // 验证 token
        if (tokenManager.checkToken(model)) {
            // 如果 token 验证成功，将 token 对应的用户 id 存在 request 中，便于之后注入
            request.setAttribute(Constant.CURRENT_USER_ID, model.getUserId());
            return true;
        }
        if (method.getAnnotation(Authorization.class) != null) {
            try {
                ResponseUtil.write(response, HttpServletResponse.SC_UNAUTHORIZED, Constant.RESPONSE_AUTH_MSG);
//                throw new BusinessException(ErrorsEnum.EX_9999.getCode(),ErrorsEnum.EX_9999.getMessage());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
