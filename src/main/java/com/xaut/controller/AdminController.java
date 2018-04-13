package com.xaut.controller;

import com.xaut.constant.HeaderConstant;
import com.xaut.dao.UserInfoDao;
import com.xaut.dto.TokenModel;
import com.xaut.entity.UserInfo;
import com.xaut.manager.TokenManager;
import com.xaut.service.ForumService;
import com.xaut.service.UserService;
import com.xaut.util.ResultBuilder;
import com.xaut.web.annotation.Authorization;
import com.xaut.web.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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


    @Autowired
    private ForumService forumService;


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

    /**
     * 管理员删帖
     *
     * @param postId 帖子ID
     * @return 成功或者失败
     */
//    @Authorization
    @RequestMapping(value = "/del/{postId}", method = RequestMethod.DELETE)
    public Object deletePost(@PathVariable int postId) {

        /**
         * todo
         */
        UserInfo userInfo = new UserInfo();
        if (forumService.delPost(userInfo,postId)) {
            return ResultBuilder.create().code(200).message("删帖成功").build();
        }
        return ResultBuilder.create().code(500).message("删帖失败").build();
    }

    /**
     * 管理员删除回帖
     *
     * @param answerId 楼层回复ID
     * @return
     */
    //    @Authorization
    @RequestMapping(value = "/reply/{answerId}", method = RequestMethod.DELETE)
    public Object delReplyPost(@PathVariable int answerId) {
        /**
         * todo 当前用户删帖
         */

        UserInfo userInfo = new UserInfo();
        if (forumService.delReplyPost(answerId)) {
            return ResultBuilder.create().code(200).message("删评论成功").build();
        }
        return ResultBuilder.create().code(500).message("删评论失败").build();
    }
}
