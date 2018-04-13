package com.xaut.service;

import com.xaut.entity.AnswerInfo;
import com.xaut.entity.PostInfo;
import com.xaut.entity.UserInfo;

import java.util.List;

/**
 * Author ： wangzhe
 * Description : 论坛服务接口
 * Version : 0.1
 */
public interface ForumService {

    /**
     * 查询所有帖子
     *
     * @return 帖子列表
     */
    List<PostInfo> selectAll();

    /**
     * 发帖
     *
     * @param userInfo 当前用户
     * @param postInfo 帖子对象
     * @return 是否发帖成功
     */
    boolean save(UserInfo userInfo, PostInfo postInfo);

    /**
     * 根据Id获取帖子详情
     *
     * @param postId 帖子Id
     * @return 回复列表
     */
    List<AnswerInfo> selectByPostId(int postId);

    /**
     * 用户删帖
     *
     * @param userInfo 当前用户
     * @param postId   帖子ID
     * @return 是否删帖成功
     */
    boolean delPost(UserInfo userInfo, int postId);

    /**
     * 用户回帖
     *
     * @param userInfo      当前用户
     * @param postId        帖子ID
     * @param answerContext 回帖内容对象
     * @return 是否回帖成功
     */
    boolean replyPost(UserInfo userInfo, int postId, String answerContext);

    /**
     * 用户删除回帖
     *
     * @param userInfo 当前用户
     * @param answerId 回帖ID
     * @return 是否删除成功
     */
    boolean delReplyPost(UserInfo userInfo, int answerId);

    /**
     * 点赞
     *
     * @param answerId 回帖ID
     * @return 是否成功
     */
    boolean doHand(int answerId);

    /**
     * 踩
     *
     * @param answerId 回帖ID
     * @return 是否成功
     */
    boolean doFoot(int answerId);
}
