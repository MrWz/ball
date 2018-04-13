package com.xaut.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xaut.entity.GameInfo;
import com.xaut.entity.UserInfo;
import com.xaut.service.GameService;
import com.xaut.util.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author : wangzhe
 * Description :
 * Version :
 */
@RestController
@RequestMapping("/game/v1")
public class GameController {

    private final GameService gameService;

    @Autowired
    public GameController(GameService gameService) {
        this.gameService = gameService;
    }

    /**
     * 获取比赛列表
     *
     * @param pn            页码
     * @param pageSize      页大小
     * @param navigatePages 分页数量
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") Integer pn,
                       @RequestParam(defaultValue = "7") Integer pageSize,
                       @RequestParam(defaultValue = "5") Integer navigatePages) {
        PageHelper.startPage(pn, pageSize);
        List<GameInfo> allGame = gameService.selectAll();
        PageInfo<GameInfo> page = new PageInfo<>(allGame, navigatePages);
        return ResultBuilder.create().code(200).data("gameInfoList", page).build();
    }

    /**
     * 用户发布比赛
     *
     * @param gameInfo 比赛对象
     * @return 响应实体 {@link Object}
     */
//    @Authorization
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object release(GameInfo gameInfo, @RequestParam(value = "placeId") int id) {

        // TODO: 2018/4/13 在线用户
        UserInfo userInfo = new UserInfo();
        gameService.save(userInfo, gameInfo, id);
        return ResultBuilder.create().code(200).message("比赛发布成功").build();
    }

    /**
     * 用户加入比赛
     *
     * @param gameUid 比赛Uid
     * @return 响应实体 {@link Object}
     */
//    @Authorization
    @RequestMapping(value = "/join", method = RequestMethod.POST)
    public Object joinIn(@RequestParam(value = "gameUid") String gameUid) {

        // TODO: 2018/4/13 在线用户
        UserInfo userInfo = new UserInfo();
        if (gameService.join(userInfo, gameUid)) {
            return ResultBuilder.create().code(200).message("成功加入比赛").build();
        }
        return ResultBuilder.create().code(500).message("加入失败").build();
    }

    /**
     * 用户退出比赛
     *
     * @param gameUid 比赛Uid
     * @return 响应实体 {@link Object}
     */
//    @Authorization
    @RequestMapping(value = "/quit", method = RequestMethod.POST)
    public Object quit(@RequestParam(value = "gameUid") String gameUid) {

        // TODO: 2018/4/13 在线用户
        UserInfo userInfo = new UserInfo();
        if (gameService.quit(userInfo, gameUid)) {
            return ResultBuilder.create().code(200).message("成功退出比赛").build();
        }
        return ResultBuilder.create().code(500).message("退出失败").build();
    }

    /**
     * 发布者完成退出/取消比赛
     *
     * @param gameUid 比赛Uid
     * @return 响应实体 {@link Object}
     */
//    @Authorization
    @RequestMapping(value = "/end", method = RequestMethod.POST)
    public Object end(@RequestParam(value = "gameUid") String gameUid) {

        // TODO: 2018/4/13 在线用户
        UserInfo userInfo = new UserInfo();
        if (gameService.end(userInfo, gameUid)) {
            return ResultBuilder.create().code(200).message("成功取消比赛").build();
        }
        return ResultBuilder.create().code(500).message("取消失败").build();
    }
}
