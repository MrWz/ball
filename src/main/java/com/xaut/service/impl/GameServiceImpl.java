package com.xaut.service.impl;

import com.xaut.dao.GameInfoDao;
import com.xaut.dao.RoleInfoDao;
import com.xaut.dao.UserInfoDao;
import com.xaut.dao.UserRoleDao;
import com.xaut.dto.UserInfoDto;
import com.xaut.entity.GameInfo;
import com.xaut.entity.RoleInfo;
import com.xaut.entity.UserInfo;
import com.xaut.entity.UserRole;
import com.xaut.exception.BusinessException;
import com.xaut.exception.ExceptionCode;
import com.xaut.exception.ParameterException;
import com.xaut.service.GameService;
import com.xaut.service.UserService;
import com.xaut.util.Md5SaltUtil;
import com.xaut.util.RedisCountHotBookUtil;
import com.xaut.util.UIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import static com.xaut.exception.ExceptionCode.*;
import static com.xaut.util.RoleUtil.ROLE_1;
import static com.xaut.util.RoleUtil.ROLE_2;

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
    RedisCountHotBookUtil redisCountHotBookUtil;


    @Override
    public List<GameInfo> selectAll() {
        return gameInfoDao.selectAll();
    }

    @Override
    public boolean save(UserInfo userInfo,GameInfo gameInfo) {
        Date date = new Date();
        String uid = UIDUtil.getRandomUID();
        gameInfo.setUid(uid);
        gameInfo.setUserUid(userInfo.getUid());
        gameInfo.setType("篮球");
        gameInfo.setStartTime(date);
        gameInfo.setDescription("测试数据");
        gameInfo.setReleaseTime(date);
        gameInfo.setDeleted(false);
        gameInfo.setCreateTime(date);
        gameInfo.setUpdateTime(date);

        return gameInfoDao.insert(gameInfo);
    }
}
