package com.xaut.service;

import com.xaut.entity.AnswerInfo;
import com.xaut.entity.GameInfo;
import com.xaut.entity.PostInfo;
import com.xaut.entity.UserInfo;

import java.util.List;

/**
 * Author ： wangzhe
 * Description : 论坛服务接口
 * Version : 0.1
 */
public interface ForumService {

    List<PostInfo> selectAll();

    boolean save(UserInfo userInfo, PostInfo postInfo);

    List<AnswerInfo> selectByPostId(int postId);

    boolean delPost(UserInfo userInfo, int postId);

    boolean replyPost(UserInfo userInfo, int postId, String answerContext);

    boolean delReplyPost(UserInfo userInfo, int answerId);

    boolean doHand(int answerId);

    boolean doFoot(int answerId);
}
