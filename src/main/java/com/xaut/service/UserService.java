package com.xaut.service;


import com.xaut.dto.UserInfoDto;
import com.xaut.entity.UserInfo;

import java.util.List;

/**
 * Author ： Martin
 * Date : 17/9/26
 * Description : 授权服务接口
 * Version : 0.1
 */
public interface UserService {

    /**
     * 根据token获取用户信息
     */
    UserInfoDto authForUser(String token, String systemCode);

    List<UserInfo> getAllUser();
}
