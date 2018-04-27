package com.xaut.controller;

import com.xaut.Vo.UserAnswerVo;
import com.xaut.constant.HeaderConstant;
import com.xaut.dao.SportPlaceDao;
import com.xaut.dao.TypeInfoDao;
import com.xaut.dao.UserInfoDao;
import com.xaut.dto.TokenModel;
import com.xaut.entity.AnswerInfo;
import com.xaut.entity.GameInfo;
import com.xaut.entity.PostInfo;
import com.xaut.entity.SportPlace;
import com.xaut.entity.TypeInfo;
import com.xaut.entity.UserInfo;
import com.xaut.manager.TokenManager;
import com.xaut.service.ForumService;
import com.xaut.service.GameService;
import com.xaut.service.UserService;
import com.xaut.util.ResultBuilder;
import com.xaut.web.annotation.Authorization;
import com.xaut.web.annotation.CurrentUser;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

/**
 * Author : wangzhe
 * Date : on 2018/01/25
 * Description :
 * Version :
 */
@RestController
@RequestMapping("/user/v1")
public class UserController {

    private final UserService userService;
    private final UserInfoDao userInfoDao;
    private final TypeInfoDao typeInfoDao;
    private final SportPlaceDao sportPlaceDao;
    private final TokenManager tokenManager;
    private final GameService gameService;
    private final ForumService forumService;

    @Autowired
    public UserController(UserService userService, UserInfoDao userInfoDao, TypeInfoDao typeInfoDao, SportPlaceDao sportPlaceDao, TokenManager tokenManager, GameService gameService, ForumService forumService) {
        this.userService = userService;
        this.userInfoDao = userInfoDao;
        this.typeInfoDao = typeInfoDao;
        this.sportPlaceDao = sportPlaceDao;
        this.tokenManager = tokenManager;
        this.gameService = gameService;
        this.forumService = forumService;
    }


    /**
     * 用户注册
     *
     * @param response 响应头
     * @param username 用户名
     * @param password 密码
     * @return 响应实体 {@link Object}
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public Object register(HttpServletResponse response, String username, String password, MultipartFile picture) {
        boolean flag = userService.checkRegister(username, password, picture);
        if (flag) {
            UserInfo user = userInfoDao.selectByName(username);
            // 生成一个 token，保存用户登录状态
            TokenModel model = tokenManager.createToken(user.getUid());
            response.setHeader(HeaderConstant.X_AUTH_TOKEN, model.toString());
            response.setHeader("username", user.getName());
            /**
             *  todo
             */
            return ResultBuilder.create().code(200).message("注册成功").data("data", user).build();
        }
        return ResultBuilder.create().code(500).message("用户名已存在").build();
    }

    /**
     * 用户登录
     *
     * @param response 响应头
     * @param username 用户名
     * @param password 密码
     * @return 响应实体
     */
    @PostMapping("/login")
    public Object login(HttpServletResponse response, @RequestParam String username, @RequestParam String password) {
        boolean flag = userService.checkLogin(username, password);
        if (flag) {
            UserInfo user = userInfoDao.selectByName(username);

            // 生成一个 token，保存用户登录状态
            // TODO: 2018/4/13 token存放于cookie有bug
            TokenModel model = tokenManager.createToken(user.getUid());
            Cookie cookie = new Cookie(HeaderConstant.X_AUTH_TOKEN, model.toString());
            // 有效期,秒为单位
            cookie.setMaxAge(3600);
            response.addCookie(cookie);

            /**
             * todo
             */
            response.setHeader(HeaderConstant.X_AUTH_TOKEN, model.toString());
            response.setHeader("username", user.getName());
            return ResultBuilder.create().code(200).message("请去首页进行选购").data("data", user).build();
        }
        return ResultBuilder.create().code(500).message("密码错误").build();
    }

    /**
     * 用户退出登录
     *
     * @param user 注入的当前用户信息
     * @return 响应实体 {@link Object}
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.DELETE)
    @Authorization
    public Object loginOff(HttpServletResponse response, @CurrentUser UserInfo user) {
        tokenManager.deleteToken(user.getUid());
        return ResultBuilder.create().code(200).message("注销成功").build();
    }

    /**
     * 获取比赛类型列表
     *
     * @return 系统支持比赛类型
     */
    @RequestMapping(value = "/typeList", method = RequestMethod.GET)
    public Object typeList() {
        List<TypeInfo> typeList = typeInfoDao.selectAll();
        return ResultBuilder.create().code(200).data("data", typeList).build();
    }

    /**
     * 获取场地列表
     *
     * @return 系统场地
     */
    @RequestMapping(value = "/placeList", method = RequestMethod.GET)
    public Object placeList() {
        List<SportPlace> sportPlaceList = sportPlaceDao.selectAll();
        return ResultBuilder.create().code(200).data("data", sportPlaceList).build();
    }

    /**
     * 根据当前用户获取其参加的比赛列表
     *
     * @return 获得当前用户参加的比赛列表
     */
    @Authorization
    @RequestMapping(value = "/userGame", method = RequestMethod.GET)
    public Object userGameList(@CurrentUser UserInfo user) {
        List<GameInfo> gameInfoList = gameService.selectUserGames(user);
        return ResultBuilder.create().code(200).data("data", gameInfoList).build();
    }

    /**
     * 根据当前用户获取其发布的比赛列表
     *
     * @return 获得当前用户发布的比赛列表
     */
    @Authorization
    @RequestMapping(value = "/authorGame", method = RequestMethod.GET)
    public Object authorGameList(@CurrentUser UserInfo user) {
        List<GameInfo> gameInfoList = gameService.selectAuthorGames(user);
        return ResultBuilder.create().code(200).data("data", gameInfoList).build();
    }


    /**
     * 根据当前用户获取其发布的帖子列表
     */
    @Authorization
    @RequestMapping(value = "/userPost", method = RequestMethod.GET)
    public Object userPostList(@CurrentUser UserInfo user) {
        List<PostInfo> postInfoList = forumService.selectUserPosts(user);
        return ResultBuilder.create().code(200).data("data", postInfoList).build();
    }

    /**
     * 根据当前用户获取其发布的回复列表
     */
    @Authorization
    @RequestMapping(value = "/userAnswer", method = RequestMethod.GET)
    public Object userAnwserList(@CurrentUser UserInfo user) {
        List<AnswerInfo> answerInfoList = forumService.selectUserAnswers(user);
        List<UserAnswerVo> userAnswerVoList = new ArrayList<>();
        for (AnswerInfo answerInfo : answerInfoList) {
            UserAnswerVo userAnswerVo = new UserAnswerVo();
            PostInfo postInfo = forumService.selectPostById(answerInfo.getPostId());
            BeanUtils.copyProperties(answerInfo, userAnswerVo);
            BeanUtils.copyProperties(postInfo, userAnswerVo);
            userAnswerVoList.add(userAnswerVo);
        }

        return ResultBuilder.create().code(200).data("data", userAnswerVoList).build();
    }

    /**
     * 用户发布比赛
     *
     * @param gameInfo 比赛对象
     * @return 响应实体 {@link Object}
     */
    @Authorization
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object release(@CurrentUser UserInfo user, GameInfo gameInfo, @RequestParam(value = "placeId") int placeId) {

        UserInfo userInfo = new UserInfo();
        if (gameService.save(userInfo, gameInfo, placeId)) {
            return ResultBuilder.create().code(200).message("比赛发布成功").build();
        }
        return ResultBuilder.create().code(500).message("比赛发布失败，请重新发布").build();
    }
}
