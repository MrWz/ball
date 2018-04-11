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

    List<GameInfo> selectAll();

    boolean save(UserInfo userInfo, GameInfo gameInfo, int placeId);

    boolean join(UserInfo userInfo, String gameUid);

    boolean quit(UserInfo userInfo, String gameUid);

    boolean end(UserInfo userInfo, String gameUid);
}
