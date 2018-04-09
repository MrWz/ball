package com.xaut.dao;

import com.xaut.entity.AnswerInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnswerInfoDao {
    int deleteByPrimaryKey(Integer id);

    boolean insert(AnswerInfo record);

    AnswerInfo selectByPrimaryKey(Integer id);

    List<AnswerInfo> selectAll();

    boolean updateByPrimaryKey(AnswerInfo record);

    List<AnswerInfo> selectByPostId(int postId);
}