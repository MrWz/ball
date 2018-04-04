package com.xaut.web.resolver;

import com.xaut.constant.Constant;
import com.xaut.dao.UserInfoDao;
import com.xaut.entity.UserInfo;
import com.xaut.exception.BusinessException;
import com.xaut.exception.ErrorsEnum;
import com.xaut.web.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;


/**
 * Author ： wangzhe
 * Date : on 2017/12/15
 * Description : 增加方法注入，将含有CurrentUser注解的方法参数注入当前登录用户
 * Version : 0.1
 */
@Component
public class CurrentUserResolver implements HandlerMethodArgumentResolver {


    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        // 如果参数类型是 User 并且有 CurrentUser 注解则支持
        if (parameter.getParameterType().isAssignableFrom(UserInfo.class) &&
                parameter.hasParameterAnnotation(CurrentUser.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        // 取出鉴权时存入的登录用户 Id
        String currentUserId = (String) webRequest.getAttribute(Constant.CURRENT_USER_ID, RequestAttributes.SCOPE_REQUEST);
        if (currentUserId != null) {
            // 从数据库中查询并返回
            return userInfoDao.selectByUid(currentUserId);
        }
        throw new BusinessException(ErrorsEnum.EX_20007.getCode(), ErrorsEnum.EX_20007.getMessage());
    }
}
