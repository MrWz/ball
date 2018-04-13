package com.xaut.service.impl;

import com.xaut.dao.RoleInfoDao;
import com.xaut.dao.UserInfoDao;
import com.xaut.dao.UserRoleDao;
import com.xaut.dto.UserInfoDto;
import com.xaut.entity.RoleInfo;
import com.xaut.entity.UserInfo;
import com.xaut.entity.UserRole;
import com.xaut.exception.BusinessException;
import com.xaut.exception.ErrorsEnum;
import com.xaut.service.UserService;
import com.xaut.util.Md5SaltUtil;
import com.xaut.util.RedisCountHotBookUtil;
import com.xaut.util.UIDUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.regex.Pattern;

import static com.xaut.util.RoleUtil.ROLE_1;
import static com.xaut.util.RoleUtil.ROLE_2;

/**
 * Author ： wangzhe
 * Description : 用户接口服务
 * Version : 0.1
 */
@Service(value = "UserService")
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    private UserInfoDao userInfoDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Autowired
    private RoleInfoDao roleDao;

    @Autowired
    private RedisCountHotBookUtil<UserInfo> redisCountHotBookUtil;

    @Override
    public Boolean checkRegister(String username, String password, MultipartFile picture) {
        Boolean flag = StringUtils.isAnyBlank(username, password);
        if (flag || picture == null) {
            throw new BusinessException(ErrorsEnum.EX_10001.getCode(), ErrorsEnum.EX_10001.getMessage());
        }
        Pattern pattern = Pattern.compile("[0-9a-zA-Z]*");
        boolean boolName = pattern.matcher(username).matches();
        if (username.length() > 32 || !boolName) {
            throw new BusinessException(ErrorsEnum.EX_20008.getCode(), ErrorsEnum.EX_20008.getMessage());
        }
        boolean boolPwd = pattern.matcher(password).matches();
        if (password.length() < 6 || password.length() > 32 || !boolPwd) {
            throw new BusinessException(ErrorsEnum.EX_20009.getCode(), ErrorsEnum.EX_20009.getMessage());
        }

        RoleInfo role;
        UserRole userRole;
        UserInfo userInfo = userInfoDao.selectByName(username);
        if (userInfo == null) {//用户不存在
            String uid = UIDUtil.getRandomUID();
            String mdPassword = Md5SaltUtil.getMD5(password, uid);
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("f:\\task\\用户头像\\" + username + ".jpg")));//保存图片到目录下
                out.write(picture.getBytes());
                out.flush();
                out.close();
                String filename = "f:\\task\\用户头像\\" + username + ".jpg";
                Date date = new Date();
                UserInfo user = new UserInfo(uid, username, mdPassword, filename, false, date, date);
                userInfoDao.insert(user);

                redisCountHotBookUtil.putRedis(user, UserInfo.class);//用户注册后，将用户信息放到Redis中

                role = roleDao.selectByDescription(ROLE_2.getRole());
                String roleUid = role.getUid();
                userRole = new UserRole(uid, roleUid, false, date, date);
                userRoleDao.insert(userRole);
                return true;
            } catch (IOException e) {
                e.printStackTrace();
                throw new BusinessException(ErrorsEnum.EX_20010.getCode(), ErrorsEnum.EX_20010.getMessage());

            }
        } else {//用户已存在
            return false;
        }
    }

    @Override
    public Boolean checkLogin(String username, String password) {
        Boolean flag = StringUtils.isAnyBlank(username, password);
        if (flag) {
            throw new BusinessException(ErrorsEnum.EX_10001.getCode(), ErrorsEnum.EX_10001.getMessage());
        }

        UserInfo user = (UserInfo) redisCountHotBookUtil.getInRedis(username, UserInfo.class);
        /*
         先从Redis中拿用户信息，提高效率
         */
        if (user == null) {
            UserInfo us = userInfoDao.selectByName(username);
            if (us == null) {
                throw new BusinessException(ErrorsEnum.EX_200101.getCode(), ErrorsEnum.EX_200101.getMessage());
            }
            redisCountHotBookUtil.putRedis(us, UserInfo.class);
            user = us;
        }
        /*
          验证权限
         */
        String uid = user.getUid();
        UserRole userRole = userRoleDao.selectByUserUid(uid);
        RoleInfo role = roleDao.selectByPrimaryKey(userRole.getRoleUid());
        if (role.getDescription().equals(ROLE_1.getRole())) {//权限不对，抛出异常
            throw new BusinessException(ErrorsEnum.EX_30001.getCode(), ErrorsEnum.EX_30001.getMessage());
        }
        /*
          验证密码正确性
         */
        String mdPwd = user.getPassword();
        String mdPassword = Md5SaltUtil.getMD5(password, uid);
        return mdPwd.equals(mdPassword);
    }

    @Override
    public UserInfo selectByUid(String uid) {
        return userInfoDao.selectByUid(uid);
    }

    @Override
    public UserInfoDto authForUser(String token, String systemCode) {
        return new UserInfoDto(token);
    }

}
