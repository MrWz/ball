package com.xaut.service;

import com.xaut.entity.GameInfo;
import com.xaut.entity.UserInfo;

import java.util.List;

/**
 * Author ： wangzhe
 * Description : 比赛服务接口
 * Version : 0.1
 */
public interface GameService {

    /**
     * 查询比赛列表
     *
     * @return 返回比赛列表
     */
    List<GameInfo> selectAll();

    /**
     * 发布比赛
     *
     * @param userInfo 当前用户
     * @param gameInfo 比赛对象
     * @param placeId  场地ID
     * @return 发布比赛是否成功
     */
    boolean save(UserInfo userInfo, GameInfo gameInfo, int placeId);

    /**
     * 加入比赛
     *
     * @param userInfo 当前用户
     * @param gameUid  比赛Uid
     * @return 是否加入成功
     */
    boolean join(UserInfo userInfo, String gameUid);

    /**
     * 退出比赛
     *
     * @param userInfo 当前用户
     * @param gameUid  比赛Uid
     * @return 退出是否成功
     */
    boolean quit(UserInfo userInfo, String gameUid);

    /**
     * 发布者结束比赛
     *
     * @param userInfo 当前用户
     * @param gameUid  比赛Uid
     * @return 结束是否成功
     */
    boolean end(UserInfo userInfo, String gameUid);
}
