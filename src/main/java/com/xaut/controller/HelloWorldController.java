package com.xaut.controller;

import com.xaut.entity.UserInfo;
import com.xaut.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Author : wangzhe
 * Date : on 2017/12/15
 * Description :
 * Version :
 */
@Controller
public class HelloWorldController {

    @Autowired
    private UserService userService;

    @RequestMapping("/hello")
    public String say(){
        List<UserInfo> userInfoList = userService.getAllUser();
        System.out.println(userInfoList);
        return "index";
    }
}
