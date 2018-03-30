package com.xaut.controller;

import com.xaut.constant.HeaderConstant;
import com.xaut.dao.UserInfoDao;
import com.xaut.dto.TokenModel;
import com.xaut.entity.UserInfo;
import com.xaut.manager.TokenManager;
import com.xaut.service.UserService;
import com.xaut.util.ResultBuilder;
import com.xaut.web.annotation.Authorization;
import com.xaut.web.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * 管理员操作类
 */
@Controller
@RequestMapping("/admin/v1")
public class AdminController{

    @Resource
    private UserInfoDao userInfoDao;

    @Autowired
    private UserService userService;

    @Autowired
    TokenManager tokenManager;

    /**
     * 管理员登录
     *
     * @param response 响应头
     * @param username 用户名
     * @param password 密码
     * @return 响应实体 {@link Object}
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public Object login(HttpServletResponse response,
                     @RequestParam String username,
                     @RequestParam String password) {
        if (userService.checkLogin(username, password)) {
            UserInfo user = userInfoDao.selectByName(username);
            // 生成一个 token，保存用户登录状态
            TokenModel model=tokenManager.createToken(user.getUid());
            response.setHeader(HeaderConstant.X_AUTH_TOKEN, model.toString());

            return ResultBuilder.create().code(200).message("您已登录成功").data("userinfo",user).build();
        }
        return ResultBuilder.create().code(500).message("用户名或者密码错误").build();
    }

    /**
     * 管理员退出登录
     *
     * @param user 注入的当前用户信息
     * @return 响应实体 {@link Object}
     */
    @Authorization
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.DELETE)
    public Object logoff(@CurrentUser UserInfo user) {
        tokenManager.deleteToken(user.getUid());
        return ResultBuilder.create().code(200).message("注销成功").build();
    }
}
