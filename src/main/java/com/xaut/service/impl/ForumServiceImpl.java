package com.xaut.service.impl;

import com.xaut.dao.AnswerInfoDao;
import com.xaut.dao.PostInfoDao;
import com.xaut.entity.AnswerInfo;
import com.xaut.entity.GameInfo;
import com.xaut.entity.PostInfo;
import com.xaut.entity.UserInfo;
import com.xaut.service.ForumService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Author ： wangzhe
 * Description : 用户接口服务
 * Version : 0.1
 */
@Service(value = "ForumService")
@Slf4j
public class ForumServiceImpl implements ForumService {

    @Autowired
    private PostInfoDao postInfoDao;

    @Autowired
    private AnswerInfoDao answerInfoDao;

    @Override
    public List<PostInfo> selectAll() {
        return postInfoDao.selectAll();
    }

    @Override
    public boolean save(UserInfo userInfo, GameInfo gameInfo, int id) {
        return false;
    }

    @Override
    public List<AnswerInfo> selectByPostId(int postId) {
        return answerInfoDao.selectByPostId(postId);
    }
}
