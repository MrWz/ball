package com.xaut.service.impl;

import com.xaut.dao.GameInfoDao;
import com.xaut.dao.PlaceListDao;
import com.xaut.dao.SportPlaceDao;
import com.xaut.dao.UserGameDao;
import com.xaut.entity.*;
import com.xaut.exception.BusinessException;
import com.xaut.exception.ErrorsEnum;
import com.xaut.service.GameService;
import com.xaut.util.UIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author ： wangzhe
 * Description : 用户接口服务
 * Version : 0.1
 */
@Service(value = "GameService")
@Slf4j
public class GameServiceImpl implements GameService {

    @Autowired
    private GameInfoDao gameInfoDao;

    @Autowired
    private SportPlaceDao sportPlaceDao;

    @Autowired
    private UserGameDao userGameDao;

    @Autowired
    private PlaceListDao placeListDao;

    @Override
    public List<GameInfo> selectAll() {
        return gameInfoDao.selectAll();
    }

    @Override
    public boolean save(UserInfo userInfo, GameInfo gameInfo, int placeId) {
        SportPlace sportPlace = sportPlaceDao.selectByPrimaryKey(placeId);
        String type = sportPlace.getType();
        if (!type.equals(gameInfo.getType())) {
            throw new BusinessException(ErrorsEnum.EX_20011.getCode(), ErrorsEnum.EX_20011.getMessage());
        }

        Date gameInfoStartTime = gameInfo.getStartTime();
        Date gameInfoEndTime = gameInfo.getEndTime();
        if (gameInfoStartTime.after(gameInfoEndTime)) {
            throw new BusinessException(ErrorsEnum.EX_20013.getCode(), ErrorsEnum.EX_20013.getMessage());
        }
        List<PlaceList> placeLists = placeListDao.selectByPlaceId(placeId);
        for (PlaceList placeList : placeLists) {
            Date sportPlaceEndTime = placeList.getEndTime();
            Date sportPlaceStartTime = placeList.getStartTime();
            if (!((sportPlaceStartTime == null && sportPlaceEndTime == null)
                    || (gameInfoEndTime.before(sportPlaceStartTime) || gameInfoStartTime.after(sportPlaceEndTime)))) {
                throw new BusinessException(ErrorsEnum.EX_20012.getCode(), ErrorsEnum.EX_20012.getMessage());
            }
        }

        String uid = UIDUtil.getRandomUID();

        /**
         * 预定场地，记得归还哦
         */
        Date date = new Date();
        PlaceList placeList = new PlaceList();
        placeList.setGameUid(uid);
        placeList.setPlaceId(placeId);
        placeList.setStartTime(gameInfoStartTime);
        placeList.setEndTime(gameInfoEndTime);
        placeList.setDeleted(false);
        placeList.setCreateTime(date);
        placeList.setUpdateTime(date);
        placeListDao.insert(placeList);

        /**
         * todo
         */
        gameInfo.setUid(uid);
        gameInfo.setUserUid(uid);
        gameInfo.setPlace(sportPlace.getType() + "-" + sportPlace.getIdentifier());
        gameInfo.setDeleted(false);
        gameInfo.setCreateTime(date);
        gameInfo.setUpdateTime(date);
        gameInfo.setStartTime(date);
        gameInfo.setDescription("测试数据");
        int oldNum = gameInfo.getPeopleNum();
        gameInfo.setPeopleNum(--oldNum);

        UserGame userGame = new UserGame();
        userGame.setUserUid(uid);
        userGame.setGameUid(uid);
        userGame.setDeleted(false);
        userGame.setCreateTime(date);
        userGame.setUpdateTime(date);
        userGameDao.insert(userGame);

        return gameInfoDao.insert(gameInfo);
    }

    @Override
    public boolean join(UserInfo userInfo, String gameUid) {
        GameInfo gameInfo = gameInfoDao.selectByUid(gameUid);
        if (gameInfo.getPeopleNum() <= 0) {
            return false;
        }
        int oldNum = gameInfo.getPeopleNum();
        gameInfo.setPeopleNum(--oldNum);
        gameInfoDao.updateByPrimaryKey(gameInfo);
        UserGame userGame = new UserGame();
        /**
         * todo
         */
        userGame.setUserUid(gameUid);
        userGame.setGameUid(gameUid);
        userGame.setDeleted(false);
        userGame.setCreateTime(new Date());
        userGame.setUpdateTime(new Date());
        userGameDao.insert(userGame);
        return true;
    }

    @Override
    public boolean quit(UserInfo userInfo, String gameUid) {
        GameInfo gameInfo = gameInfoDao.selectByUid(gameUid);
        /**
         * todo
         */
        List<UserGame> userGameList = userGameDao.selectByUserUid(gameUid);
        for (UserGame userGame : userGameList) {
            String userGameUid = userGame.getGameUid();
            if (userGameUid.equals(gameUid)) {
                int oldNum = gameInfo.getPeopleNum();
                gameInfo.setPeopleNum(++oldNum);
                gameInfoDao.updateByPrimaryKey(gameInfo);
                userGame.setDeleted(true);
                userGameDao.updateByPrimaryKey(userGame);

                return true;
            }
        }
        return false;

    }

    @Override
    public boolean end(UserInfo userInfo, String gameUid) {

        PlaceList placeList = placeListDao.selectByGameUid(gameUid);
        placeList.setDeleted(true);
        placeListDao.updateByPrimaryKey(placeList);

        return true;
    }
}
