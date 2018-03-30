package com.xaut.controller;

import com.xaut.entity.UserInfo;
import com.xaut.service.UserService;
import com.xaut.util.RedisLockUtil;
import com.xaut.web.annotation.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Author : wangzhe
 * Date : on 2017/12/15
 * Description :
 * Version :
 */
@RestController
public class HelloWorldController {

    @Autowired
    private RedisLockUtil redisLockUtil;

    @RequestMapping(value = "/hello" , method = RequestMethod.POST)
    public String say(@RequestParam(value="name") String name,
                      @RequestParam(value="file")MultipartFile file){
        redisLockUtil.getLock("wzz",10000);
        if (!file.isEmpty()) {
            try {
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(new File("f:\\task\\用户头像\\"+name+".jpg")));//保存图片到目录下
                out.write(file.getBytes());
                out.flush();
                out.close();
                String filename="f:\\旗杯\\demo5\\src\\main\\webapp\\"+name+".jpg";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            } catch (IOException e) {
                e.printStackTrace();
                return "上传失败," + e.getMessage();
            }
            return "permanager";
        } else {
            return "上传失败，因为文件是空的.";
        }
    }

    @RequestMapping(value = "/aspect")
    @Authorization
    public void aspect(){
        System.out.println("qwe");
        System.out.println("sa");
    }
}
