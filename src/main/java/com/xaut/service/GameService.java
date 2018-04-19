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
     * 查询比赛列表
     *
     * @return 返回比赛列表
     */
    List<GameInfo> selectByType(String type);

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

    /**
     * 根据当前用户获取其参加的比赛
     * @param userInfo 当前用户对象
     * @return 该用户参加的所有比赛
     */
    List<GameInfo> selectUserGames(UserInfo userInfo);

    /**
     * 根据当前用户获取其发布的比赛
     * @param userInfo 当前用户对象
     * @return 该用户发布的所有比赛
     */
    List<GameInfo> selectAuthorGames(UserInfo userInfo);
}
