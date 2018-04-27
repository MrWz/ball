package com.xaut.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xaut.Vo.UserAndPostListVo;
import com.xaut.Vo.UserPostDetailVo;
import com.xaut.dao.AnswerInfoDao;
import com.xaut.dao.PostInfoDao;
import com.xaut.entity.AnswerInfo;
import com.xaut.entity.PostInfo;
import com.xaut.entity.UserInfo;
import com.xaut.service.ForumService;
import com.xaut.service.UserService;
import com.xaut.util.RedisTopTenUtil;
import com.xaut.util.ResultBuilder;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * Created by user on 2018/4/8.
 */
@RestController
@RequestMapping("/forum/v1")
public class ForumController {

    private final ForumService forumService;
    private final UserService userService;
    private final RedisTopTenUtil redisTopTenUtil;
    private final PostInfoDao postInfoDao;
    private final AnswerInfoDao answerInfoDao;
    @Autowired
    public ForumController(ForumService forumService,
                           UserService userService,
                           RedisTopTenUtil redisTopTenUtil, PostInfoDao postInfoDao, AnswerInfoDao answerInfoDao) {
        this.forumService = forumService;
        this.userService = userService;
        this.redisTopTenUtil = redisTopTenUtil;
        this.postInfoDao = postInfoDao;
        this.answerInfoDao = answerInfoDao;
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
        List<UserAndPostListVo> userAndPostListVooList = new ArrayList<>();
        for (PostInfo postInfo : allPost) {
            UserAndPostListVo userAndPostListVo = new UserAndPostListVo();
            String userUid = postInfo.getUserUid();
            UserInfo userInfo = userService.selectByUid(userUid);
            BeanUtils.copyProperties(postInfo, userAndPostListVo);
            BeanUtils.copyProperties(userInfo, userAndPostListVo);
            userAndPostListVooList.add(userAndPostListVo);
        }
        PageInfo<UserAndPostListVo> pagePost = new PageInfo<>(userAndPostListVooList, navigatePages);
        return ResultBuilder.create().code(200).data("data", pagePost).build();
    }

    /**
     * 获取帖子详情
     *
     * @param postId 帖子ID
     * @return 回复楼层信息
     */
    @RequestMapping(value = "/detail/{postId}", method = RequestMethod.POST)
    public Object list(@PathVariable int postId) {
        PostInfo postInfo = forumService.selectPostById(postId);
        List<AnswerInfo> answerInfoList = forumService.selectByPostId(postId);
        redisTopTenUtil.putRedisTopTen(postId);
        List<UserPostDetailVo> userPostDetailList = new ArrayList<>();

        for (AnswerInfo answerInfo : answerInfoList) {
            UserPostDetailVo userPostDetailVo = new UserPostDetailVo();
            String userUid = answerInfo.getUserUid();
            UserInfo userInfo = userService.selectByUid(userUid);
            BeanUtils.copyProperties(userInfo, userPostDetailVo);
            BeanUtils.copyProperties(answerInfo, userPostDetailVo);
            userPostDetailList.add(userPostDetailVo);
        }
        return ResultBuilder.create().code(200).data("post", postInfo)
                .data("data", userPostDetailList).build();
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

    /**
     * 获取帖子TOP-TEN
     *
     * @return 帖子点击量最多的10个帖子，不够是个返回全部
     */
    @RequestMapping(value = "/topTen", method = RequestMethod.GET)
    public Object topTen() {
        Set<String> postIds = redisTopTenUtil.getInRedisTopTen();
        List<PostInfo> allPost = new ArrayList<>();
        for (String postIdStr : postIds) {
            int postID = Integer.parseInt(postIdStr);
            PostInfo postInfo = postInfoDao.selectByPrimaryKey(postID);
            allPost.add(postInfo);
        }
        Collections.reverse(allPost);
        return ResultBuilder.create().code(200).data("data", allPost).build();
    }
    /**
     * 获取某个帖子点赞数最多的10条回复
     *
     * @return 某个帖子点赞数最多的10条回复，不够是个返回全部
     */
    @RequestMapping(value = "/hotReply/{postID}", method = RequestMethod.POST)
    public Object hotReply(@PathVariable int postID) {
        List<AnswerInfo> allAnswer= answerInfoDao.selectByPostIdTopten(postID);
        return ResultBuilder.create().code(200).data("data", allAnswer).build();
    }
}
