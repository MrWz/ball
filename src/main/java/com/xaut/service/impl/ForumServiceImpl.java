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

import java.util.Date;
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
    public boolean save(UserInfo userInfo, PostInfo postInfo) {
        Date date = new Date();
        /**
         * todo
         */
        postInfo.setUserUid("111111111111");
        postInfo.setCreateTime(date);
        postInfo.setUpdateTime(date);
        postInfo.setNum(0);
        postInfo.setDeleted(false);
        return postInfoDao.insert(postInfo);
    }

    @Override
    public List<AnswerInfo> selectByPostId(int postId) {
        return answerInfoDao.selectByPostId(postId);
    }

    @Override
    public boolean delPost(int postId) {
        PostInfo postInfo = postInfoDao.selectByPrimaryKey(postId);
        postInfo.setUpdateTime(new Date());
        postInfo.setDeleted(true);
        return postInfoDao.updateByPrimaryKey(postInfo);
    }

    @Override
    public boolean replyPost(UserInfo userInfo, int postId, String answerContext) {
        AnswerInfo answerInfo = new AnswerInfo();
        Date date = new Date();
        answerInfo.setUserUid("1111111");
        answerInfo.setPostId(postId);
        answerInfo.setAnswerContext(answerContext);
        answerInfo.setHandNum(0);
        answerInfo.setFootNum(0);
        answerInfo.setDeleted(false);
        answerInfo.setCreateTime(date);
        answerInfo.setUpdateTime(date);

        return answerInfoDao.insert(answerInfo);
    }

    @Override
    public boolean delReplyPost(int answerId) {
        AnswerInfo answerInfo = answerInfoDao.selectByPrimaryKey(answerId);
        answerInfo.setUpdateTime(new Date());
        answerInfo.setDeleted(true);

        return answerInfoDao.updateByPrimaryKey(answerInfo);
    }

    @Override
    public boolean doHand(int answerId) {
        AnswerInfo answerInfo = answerInfoDao.selectByPrimaryKey(answerId);
        int oldNum = answerInfo.getHandNum();
        answerInfo.setHandNum(++oldNum);

        return answerInfoDao.updateByPrimaryKey(answerInfo);
    }

    @Override
    public boolean doFoot(int answerId) {
        AnswerInfo answerInfo = answerInfoDao.selectByPrimaryKey(answerId);
        int oldNum = answerInfo.getFootNum();
        int newNum = ++oldNum;
        if(newNum >= 100){
            return delReplyPost(answerId);
        }
        answerInfo.setFootNum(newNum);
        return answerInfoDao.updateByPrimaryKey(answerInfo);
    }
}
