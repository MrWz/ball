package com.xaut.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xaut.entity.AnswerInfo;
import com.xaut.entity.PostInfo;
import com.xaut.service.ForumService;
import com.xaut.util.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by user on 2018/4/8.
 */
@RestController
@RequestMapping("/forum/v1")
public class ForumController {

    @Autowired
    private ForumService forumService;

    /**
     * 获取论坛列表
     *
     * @param pn            页码
     * @param pageSize      页大小
     * @param navigatePages 分页数量
     * @return
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") Integer pn,
                       @RequestParam(defaultValue = "7") Integer pageSize,
                       @RequestParam(defaultValue = "5") Integer navigatePages) {
        PageHelper.startPage(pn, pageSize);
        List<PostInfo> allBook = forumService.selectAll();
        PageInfo page = new PageInfo(allBook, navigatePages);
        /**
         * todo 返回值
         */
        return ResultBuilder.create().code(200).data("page", page).build();
    }

    /**
     * 获取图书详情
     *
     * @param postId
     * @return
     */
    @RequestMapping(value = "/detail/{postId}", method = RequestMethod.GET)
    public Object list(@PathVariable int postId) {
        List<AnswerInfo> answerInfoList = forumService.selectByPostId(postId);
        return ResultBuilder.create().code(200).data("answerInfoList", answerInfoList).build();
    }

}
