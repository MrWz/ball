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

    boolean save(UserInfo userInfo, GameInfo gameInfo, int id);

    List<AnswerInfo> selectByPostId(int postId);
}
