package com.xaut.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xaut.entity.AnswerInfo;
import com.xaut.entity.PostInfo;
import com.xaut.entity.UserInfo;
import com.xaut.service.ForumService;
import com.xaut.service.UserService;
import com.xaut.util.ResultBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by user on 2018/4/8.
 */
@RestController
@RequestMapping("/forum/v1")
public class ForumController {

    private final ForumService forumService;

    private final UserService userService;

    @Autowired
    public ForumController(ForumService forumService, UserService userService) {
        this.forumService = forumService;
        this.userService = userService;
    }

    /**
     * 获取帖子列表
     *
     * @param pn            页码
     * @param pageSize      页大小
     * @param navigatePages 分页数量
     * @return 所有的帖子
     */
    @RequestMapping("/list")
    public Object list(@RequestParam(defaultValue = "1") Integer pn,
                       @RequestParam(defaultValue = "3") Integer pageSize,
                       @RequestParam(defaultValue = "5") Integer navigatePages) {
        PageHelper.startPage(pn, pageSize);
        List<PostInfo> allPost = forumService.selectAll();
        List<UserInfo> userInfoList = new ArrayList<>();
        for (PostInfo postInfo : allPost) {
            String userUid = postInfo.getUserUid();
            userInfoList.add(userService.selectByUid(userUid));
        }
        PageInfo<PostInfo> pagePost = new PageInfo<>(allPost, navigatePages);
        // TODO: 2018/4/13 返回值
        return ResultBuilder.create().code(200).data("postInfoList", pagePost).data("userInfoList", userInfoList).build();
    }

    /**
     * 获取帖子详情
     *
     * @param postId 帖子ID
     * @return 回复楼层信息
     */
    @RequestMapping(value = "/detail/{postId}", method = RequestMethod.POST)
    public Object list(@PathVariable int postId) {
        List<AnswerInfo> answerInfoList = forumService.selectByPostId(postId);
        List<UserInfo> userInfoList = new ArrayList<>();
        for (AnswerInfo answerInfo : answerInfoList) {
            String userUid = answerInfo.getUserUid();
            userInfoList.add(userService.selectByUid(userUid));
        }
        // TODO: 2018/4/13 返回值
        return ResultBuilder.create().code(200).data("answerInfoList", answerInfoList).data("userInfoList", userInfoList).build();
    }

    /**
     * 用户发贴
     *
     * @param postInfo 帖子对象
     * @return 成功或者失败
     */
//    @Authorization
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public Object releasePost(PostInfo postInfo) {

        // TODO: 2018/4/13 在线用户
        UserInfo userInfo = new UserInfo();
        if (forumService.save(userInfo, postInfo)) {
            return ResultBuilder.create().code(200).message("发帖成功").build();
        }
        return ResultBuilder.create().code(500).message("发帖失败").build();
    }

    /**
     * 用户删帖
     *
     * @param postId 帖子ID
     * @return 成功或者失败
     */
//    @Authorization
    @RequestMapping(value = "/del/{postId}", method = RequestMethod.DELETE)
    public Object deletePost(@PathVariable int postId) {

        // TODO: 2018/4/12 在线用户
        UserInfo userInfo = new UserInfo();
        if (forumService.delPost(userInfo, postId)) {
            return ResultBuilder.create().code(200).message("删帖成功").build();
        }
        return ResultBuilder.create().code(500).message("删帖失败").build();
    }

    /**
     * 用户回帖
     *
     * @param postId        帖子ID
     * @param answerContext 回帖内容
     * @return
     */
    //    @Authorization
    @RequestMapping(value = "/reply/{postId}", method = RequestMethod.POST)
    public Object replyPost(@PathVariable int postId, String answerContext) {
        // TODO: 2018/4/13 在线用户
        UserInfo userInfo = new UserInfo();
        if (forumService.replyPost(userInfo, postId, answerContext)) {
            return ResultBuilder.create().code(200).message("回帖成功").build();
        }
        return ResultBuilder.create().code(500).message("回帖失败").build();
    }

    /**
     * 删除回帖
     *
     * @param answerId 楼层回复ID
     * @return
     */
    //    @Authorization
    @RequestMapping(value = "/reply/{answerId}", method = RequestMethod.DELETE)
    public Object delReplyPost(@PathVariable int answerId) {
        // TODO: 2018/4/12 在线用户
        UserInfo userInfo = new UserInfo();
        if (forumService.delReplyPost(userInfo, answerId)) {
            return ResultBuilder.create().code(200).message("删评论成功").build();
        }
        return ResultBuilder.create().code(500).message("删评论失败").build();
    }

    /**
     * 用户点赞
     *
     * @param answerId 楼层回复ID
     * @return
     */
    @RequestMapping(value = "/doHand/{answerId}", method = RequestMethod.POST)
    public Object doHand(@PathVariable int answerId) {
        forumService.doHand(answerId);
        return ResultBuilder.create().code(200).message("点赞成功").build();
    }

    /**
     * 用户点踩
     *
     * @param answerId 楼层回复ID
     * @return
     */
    @RequestMapping(value = "/doFoot/{answerId}", method = RequestMethod.POST)
    public Object dFoot(@PathVariable int answerId) {
        forumService.doFoot(answerId);
        return ResultBuilder.create().code(200).message("点踩成功").build();
    }
}
