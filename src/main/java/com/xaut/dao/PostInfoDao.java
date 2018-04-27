package com.xaut.dao;

import com.xaut.entity.PostInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostInfoDao {
    int deleteByPrimaryKey(Integer id);

    Boolean insert(PostInfo record);

    PostInfo selectByPrimaryKey(Integer id);

    List<PostInfo> selectAll();

    List<PostInfo> selectByUserUid(String userUid);

    boolean updateByPrimaryKey(PostInfo record);
}