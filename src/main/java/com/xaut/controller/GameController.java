package com.xaut.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xaut.entity.GameInfo;
import com.xaut.entity.UserInfo;
import com.xaut.service.GameService;
import com.xaut.util.ResultBuilder;
import com.xaut.web.annotation.Authorization;
import com.xaut.web.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Author : wangzhe
 * Date : on 2018/01/25
 * Description :
 * Version :
 */
@RestController
@RequestMapping("/game/v1")
public class GameController {

    @Autowired
    private GameService gameService;

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
        List<GameInfo> allBook = gameService.selectAll();
        PageInfo page = new PageInfo(allBook, navigatePages);
        return ResultBuilder.create().code(200).data("page", page).build();
    }

    /**
     * 用户发布比赛
     *
     * @param gameInfo 比赛对象
     * @return 响应实体 {@link Object}
     */
    @Authorization
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object release(@CurrentUser UserInfo userInfo, GameInfo gameInfo) {

        if (gameService.save(userInfo,gameInfo)) {
            return ResultBuilder.create().code(200).message("比赛发布成功").build();
        }
        return ResultBuilder.create().code(500).message("比赛发布失败，请重新发布").build();
    }

}
