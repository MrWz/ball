package com.xaut.controller;

import com.xaut.constant.HeaderConstant;
import com.xaut.dao.SportPlaceDao;
import com.xaut.dao.UserInfoDao;
import com.xaut.dto.TokenModel;
import com.xaut.entity.SportPlace;
import com.xaut.entity.UserInfo;
import com.xaut.manager.TokenManager;
import com.xaut.service.ForumService;
import com.xaut.service.SportPlaceService;
import com.xaut.service.UserService;
import com.xaut.util.ResultBuilder;
import com.xaut.web.annotation.Authorization;
import com.xaut.web.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 管理员操作类
 */
@Controller
@RequestMapping("/admin/v1")
public class AdminController {

    private final UserInfoDao userInfoDao;
    private final UserService userService;
    private final TokenManager tokenManager;
    private final ForumService forumService;
    private final SportPlaceService sportPlaceService;
    private final SportPlaceDao sportPlaceDao;

    @Autowired
    public AdminController(UserService userService, TokenManager tokenManager, ForumService forumService, UserInfoDao userInfoDao, SportPlaceService sportPlaceService, SportPlaceDao sportPlaceDao) {
        this.userService = userService;
        this.tokenManager = tokenManager;
        this.forumService = forumService;
        this.userInfoDao = userInfoDao;
        this.sportPlaceService = sportPlaceService;
        this.sportPlaceDao = sportPlaceDao;
    }

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
            TokenModel model = tokenManager.createToken(user.getUid());
            response.setHeader(HeaderConstant.X_AUTH_TOKEN, model.toString());

            return ResultBuilder.create().code(200).message("您已登录成功").data("userinfo", user).build();
        }
        return ResultBuilder.create().code(500).message("密码错误").build();
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
    @ResponseBody
    @RequestMapping(value = "/del/{postId}", method = RequestMethod.DELETE)
    public Object deletePost(@PathVariable int postId) {

        /**
         * todo
         */
        UserInfo userInfo = new UserInfo();
        if (forumService.delPost(userInfo, postId)) {
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
    @ResponseBody
    @RequestMapping(value = "/reply/{answerId}", method = RequestMethod.DELETE)
    public Object delReplyPost(@PathVariable int answerId) {
        /**
         * todo 当前用户删帖
         */

        UserInfo userInfo = new UserInfo();
        if (forumService.delReplyPost(userInfo, answerId)) {
            return ResultBuilder.create().code(200).message("删评论成功").build();
        }
        return ResultBuilder.create().code(500).message("删评论失败").build();
    }

    /**
     * 获取场地列表
     *
     * @return 系统场地
     */
    @RequestMapping(value = "/placeList", method = RequestMethod.GET)
    public Object placeList() {
        List<SportPlace> sportPlaceList = sportPlaceDao.selectAll();
        return ResultBuilder.create().code(200).data("typeInfoList", sportPlaceList).build();
    }

    /**
     * 管理员录入场地信息
     *
     * @param sportPlace 场地对象
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add/sportPlace", method = RequestMethod.POST)
    public Object delReplyPost(SportPlace sportPlace) {
        sportPlaceService.save(sportPlace);
        return ResultBuilder.create().code(200).message("录入场地成功").build();
    }
}
