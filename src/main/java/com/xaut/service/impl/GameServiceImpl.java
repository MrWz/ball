package com.xaut.service.impl;

import com.xaut.dao.GameInfoDao;
import com.xaut.dao.SportPlaceDao;
import com.xaut.entity.GameInfo;
import com.xaut.entity.SportPlace;
import com.xaut.entity.UserInfo;
import com.xaut.exception.BusinessException;
import com.xaut.exception.ErrorsEnum;
import com.xaut.service.GameService;
import com.xaut.util.RedisCountHotBookUtil;
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
    RedisCountHotBookUtil redisCountHotBookUtil;


    @Override
    public List<GameInfo> selectAll() {
        return gameInfoDao.selectAll();
    }

    @Override
    public boolean save(UserInfo userInfo, GameInfo gameInfo, int id) {

        SportPlace sportPlace = sportPlaceDao.selectByPrimaryKey(id);
        String type = sportPlace.getType();
        if (!type.equals(gameInfo.getType())) {
            throw new BusinessException(ErrorsEnum.EX_20011.getCode(), ErrorsEnum.EX_20011.getMessage());
        }
        Date sportPlaceEndTime = sportPlace.getEndTime();
        Date sportPlaceStartTime = sportPlace.getStartTime();
        Date gameInfoStartTime = gameInfo.getStartTime();
        Date gameInfoEndTime = gameInfo.getEndTime();
        if ((sportPlaceStartTime != null && sportPlaceEndTime != null)
                && (gameInfoStartTime.before(sportPlaceEndTime) || gameInfoEndTime.after(sportPlaceStartTime))) {
            throw new BusinessException(ErrorsEnum.EX_20012.getCode(), ErrorsEnum.EX_20012.getMessage());
        }
        /**
         * 预定场地，记得归还哦
         */
        sportPlace.setStartTime(gameInfoStartTime);
        sportPlace.setEndTime(gameInfoEndTime);
        sportPlaceDao.updateByPrimaryKey(sportPlace);
        Date date = new Date();
        String uid = UIDUtil.getRandomUID();
        gameInfo.setUid(uid);
        gameInfo.setUserUid(uid);
        gameInfo.setPlace(sportPlace.getType() + sportPlace.getIdentifier());
        gameInfo.setDeleted(false);
        gameInfo.setCreateTime(date);
        gameInfo.setUpdateTime(date);

        gameInfo.setStartTime(date);
        gameInfo.setDescription("测试数据");

        return gameInfoDao.insert(gameInfo);
    }
}
