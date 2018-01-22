package com.xaut.dao;

import com.xaut.entity.UserLabel;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserLabelDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLabel record);

    UserLabel selectByPrimaryKey(Integer id);

    List<UserLabel> selectAll();

    int updateByPrimaryKey(UserLabel record);
}