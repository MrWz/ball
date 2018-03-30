package com.xaut.web.interceptor;

import com.xaut.constant.Constant;
import com.xaut.constant.HeaderConstant;
import com.xaut.dto.TokenModel;
import com.xaut.manager.TokenManager;
import com.xaut.util.ResponseUtil;
import com.xaut.web.annotation.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * 自定义拦截器，对请求进行身份验证
 */
public class AuthorizationInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private TokenManager tokenManager;

    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response, Object handler) {
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 从 header 中得到 token
        String authentication = request.getHeader((HeaderConstant.X_AUTH_TOKEN));

        TokenModel model = tokenManager.getToken(authentication);
        /**
         * todo
         */
        if(model == null){
            return true;
        }
        // 验证 token
        if (tokenManager.checkToken(model.toString())) {
            // 如果 token 验证成功，将 token 对应的用户 id 存在 request 中，便于之后注入
            request.setAttribute(Constant.CURRENT_USER_ID, model.getUserId());
            return true;
        }
        if (method.getAnnotation(Authorization.class) != null) {
            try {
                ResponseUtil.write(response, HttpServletResponse.SC_UNAUTHORIZED, Constant.RESPONSE_AUTH_MSG);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
        return true;
    }
}
