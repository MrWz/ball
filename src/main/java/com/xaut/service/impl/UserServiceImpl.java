package com.xaut.service.impl;

import com.xaut.dao.UserInfoDao;
import com.xaut.dto.UserInfoDto;
import com.xaut.entity.UserInfo;
import com.xaut.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author ： rentao
 * Date : 17/9/26
 * Description : 用户接口服务
 * Version : 0.1
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Override
    public List<UserInfo> getAllUser() {

        return userInfoDao.selectAll();
    }

    @Override
    public UserInfoDto authForUser(String token, String systemCode){
        UserInfoDto result = new UserInfoDto(token);
        return result;
    }

}
