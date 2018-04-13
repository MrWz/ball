package com.xaut.service;


import com.xaut.dto.UserInfoDto;
import com.xaut.entity.UserInfo;
import org.springframework.web.multipart.MultipartFile;

/**
 * Author ： wangzhe
 * Description : 用户服务接口
 * Version : 0.1
 */
public interface UserService {

    /**
     * 注册
     *
     * @param username 用户名
     * @param password 密码
     * @return 注册结果
     */
    Boolean checkRegister(String username, String password, MultipartFile file);

    /**
     * 登录
     *
     * @param username 用户名
     * @param password 密码
     * @return 登录结果
     */
    Boolean checkLogin(String username, String password);

    /**
     * 查询
     *
     * @param uid 用户Uid
     * @return 用户实体
     */
    UserInfo selectByUid(String uid);

    /**
     * 根据token获取用户信息
     */
    UserInfoDto authForUser(String token, String systemCode);
}
