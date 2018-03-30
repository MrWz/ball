package com.xaut.web.resolver;

import com.xaut.constant.Constant;
import com.xaut.constant.HeaderConstant;
import com.xaut.dto.UserInfoDto;
import com.xaut.exception.ErrorsEnum;
import com.xaut.exception.WebAppException;
import com.xaut.web.annotation.CurrentUser;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;


/**
 * Author ： wangzhe
 * Date : on 2017/12/15
 * Description : 组装注解CurrentUser
 * Version : 0.1
 */
@Slf4j
public class CurrentUserResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter != null && parameter.hasParameterAnnotation(CurrentUser.class);
    }

    /**
     * 从request中获取token，并获取用户信息，装配到参数对象中
     */
    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String tokens = webRequest.getHeader(HeaderConstant.X_AUTH_TOKEN);
        String nsmr = webRequest.getHeader("username");
        String token = StringUtils.trimToEmpty(webRequest.getHeader(HeaderConstant.X_AUTH_TOKEN));
        if (StringUtils.isBlank(token)) {
            throw new WebAppException(ErrorsEnum.REQUEST_UNAUTHORIZED);
        }
        
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        UserInfoDto user = (UserInfoDto) request.getAttribute(Constant.CURRENT_USER);
        if (user == null || StringUtils.isEmpty(user.getToken())) {
            throw new WebAppException(ErrorsEnum.REQUEST_UNAUTHORIZED);
        }

        return user;
    }
}
