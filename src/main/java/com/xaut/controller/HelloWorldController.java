package com.xaut.controller;

import com.xaut.entity.UserInfo;
import com.xaut.service.UserService;
import com.xaut.ueditor.ActionEnter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
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

    @RequestMapping(value="/config")
    public void config(HttpServletRequest request, HttpServletResponse response) {
        response.setContentType("application/json");
        String rootPath = request.getSession().getServletContext().getRealPath("/");
        try {
            String exec = new ActionEnter(request, rootPath).exec();
            PrintWriter writer = response.getWriter();
            writer.write(exec);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
