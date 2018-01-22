package com.xaut.controller;

import com.xaut.util.ResultBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Author : wangzhe
 * Date : on 2017/12/15
 * Description :
 * Version :
 */
@RestController
@RequestMapping
public class HeartbeatController {

    @GetMapping("/health")
    public Object heartBeat() {
        return ResultBuilder.create().ok().build();
    }
}
