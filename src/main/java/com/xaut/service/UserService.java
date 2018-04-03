package com.xaut.service;


import com.xaut.dto.UserInfoDto;
import com.xaut.entity.UserInfo;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Author ： wangzhe
 * Description : 用户服务接口
 * Version : 0.1
 */
public interface UserService {

    /**
     * 根据token获取用户信息
     */
    UserInfoDto authForUser(String token, String systemCode);

    Boolean checkLogin(String username,String password);

    Boolean checkRegister(String username,String password ,MultipartFile file);

}
