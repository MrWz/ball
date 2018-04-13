package com.xaut.service.impl;

import com.xaut.dao.AnswerInfoDao;
import com.xaut.dao.PostInfoDao;
import com.xaut.dao.UserRoleDao;
import com.xaut.entity.*;
import com.xaut.exception.BusinessException;
import com.xaut.exception.ErrorsEnum;
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

    @Autowired
    private UserRoleDao userRoleDao;

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
    public boolean delPost(UserInfo userInfo, int postId) {
        PostInfo postInfo = postInfoDao.selectByPrimaryKey(postId);
        String userUid = postInfo.getUserUid();

        UserRole userRole = userRoleDao.selectByUserUid(userUid);
        String roleUid = userRole.getRoleUid();

        if (!(userUid.equals(userInfo.getUid()) && roleUid.equals("2"))) {
            throw new BusinessException(ErrorsEnum.EX_20014.getCode(), ErrorsEnum.EX_20014.getMessage());
        }
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
    public boolean delReplyPost(UserInfo userInfo, int answerId) {
        AnswerInfo answerInfo = answerInfoDao.selectByPrimaryKey(answerId);
        String userUid = answerInfo.getUserUid();
        UserRole userRole = userRoleDao.selectByUserUid(userInfo.getUid());
        String roleUid = userRole.getRoleUid();

        if (!(userUid.equals(userInfo.getUid()) && roleUid.equals("2"))) {
            throw new BusinessException(ErrorsEnum.EX_20014.getCode(), ErrorsEnum.EX_20014.getMessage());
        }

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
        UserInfo userInfo = new UserInfo();
        // TODO: 2018/4/12 可能存在bug，未测试
        userInfo.setUid("1");
        AnswerInfo answerInfo = answerInfoDao.selectByPrimaryKey(answerId);
        int oldNum = answerInfo.getFootNum();
        int newNum = ++oldNum;
        if (newNum >= 100) {
            return delReplyPost(userInfo,answerId);
        }
        answerInfo.setFootNum(newNum);
        return answerInfoDao.updateByPrimaryKey(answerInfo);
    }
}
