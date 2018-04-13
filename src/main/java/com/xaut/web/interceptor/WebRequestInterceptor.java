package com.xaut.web.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Author ： wangzhe
 * Description : 请求日志拦截记录
 * Version : 0.1
 */
@Slf4j
public class WebRequestInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String ip = request.getRemoteAddr();
        long startTime = System.currentTimeMillis();
        request.setAttribute("requestStartTime", startTime);
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!request.getServletPath().equals("/health")) {
            log.debug(String.format("用户: %s, 访问目标: %s.%s",
                    ip, method.getDeclaringClass().getName(), method.getName()));
        }
        return true;
    }

    @SuppressWarnings("rawtypes")
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        long start = (long) request.getAttribute("requestStartTime");
        long end = System.currentTimeMillis();
        long executeTime = end - start;
        log.debug(String.format("请求结束: %s.%s 耗时:%s ms",
                method.getDeclaringClass().getName(), method.getName(), executeTime));
    }

}
