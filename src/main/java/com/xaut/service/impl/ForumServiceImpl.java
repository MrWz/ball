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
 * Description : 论坛接口服务
 * Version : 0.1
 */
@Service(value = "ForumService")
@Slf4j
public class ForumServiceImpl implements ForumService {

    private final PostInfoDao postInfoDao;
    private final AnswerInfoDao answerInfoDao;
    private final UserRoleDao userRoleDao;

    @Autowired
    public ForumServiceImpl(PostInfoDao postInfoDao, AnswerInfoDao answerInfoDao, UserRoleDao userRoleDao) {
        this.postInfoDao = postInfoDao;
        this.answerInfoDao = answerInfoDao;
        this.userRoleDao = userRoleDao;
    }

    @Override
    public List<PostInfo> selectAll() {
        return postInfoDao.selectAll();
    }

    @Override
    public List<PostInfo> selectUserPosts(UserInfo userInfo) {
        //TODO 在线用户
        userInfo.setUid("e05276cc0e504c719bf5c05ceb36731f");
        return postInfoDao.selectByUserUid(userInfo.getUid());
    }

    @Override
    public List<AnswerInfo> selectUserAnswers(UserInfo userInfo) {
        //TODO 在线用户
        userInfo.setUid("cca6148145e04362bd44a1de59c675e4");
        return answerInfoDao.selectByUserUid(userInfo.getUid());

    }

    @Override
    public boolean save(UserInfo userInfo, PostInfo postInfo) {
        if (postInfo == null) {
            throw new BusinessException(ErrorsEnum.EX_10001.getCode(), ErrorsEnum.EX_10001.getMessage());
        }
        Date date = new Date();
        // TODO: 2018/4/13 当前用户
        // TODO: 2018/4/13 对帖子内容和标题内容进行非法检测
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

    // TODO: 2018/4/26 未测试
    @Override
    public PostInfo selectPostById(int postId) {
        return postInfoDao.selectByPrimaryKey(postId);
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
        if (answerContext == null) {
            throw new BusinessException(ErrorsEnum.EX_10001.getCode(), ErrorsEnum.EX_10001.getMessage());
        }
        // TODO: 2018/4/13 在线用户
        // TODO: 2018/4/13 对帖子内容和标题内容进行非法检测
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
