package com.xaut.dao;

import com.xaut.entity.UserGroup;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGroupDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGroup record);

    UserGroup selectByPrimaryKey(Integer id);

    List<UserGroup> selectAll();

    int updateByPrimaryKey(UserGroup record);
}