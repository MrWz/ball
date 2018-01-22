package com.xaut.controller;

import com.xaut.constant.HeaderConstant;
import com.xaut.dto.TokenModel;
import com.xaut.dto.UserInfoDto;
import com.xaut.manager.TokenManager;
import com.xaut.web.annotation.CurrentUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

/**
 * Author : wangzhe
 * Date : on 2018/01/25
 * Description :
 * Version :
 */
@RestController
@RequestMapping("/user/v1")
public class UserController {

    @Autowired
    TokenManager tokenManager;
    @PostMapping("/login")
    public String login(HttpServletResponse response, @RequestParam String name, @RequestParam String password){
        System.out.println(name);
        System.out.println(password);
        TokenModel tokenModel=tokenManager.createToken(name,password);
        response.setHeader(HeaderConstant.X_AUTH_TOKEN,tokenModel.toString());
        return "login success...";
    }

    @RequestMapping("/buy")
    public String buy(@CurrentUser UserInfoDto a){
        return "buy success";
    }
}
