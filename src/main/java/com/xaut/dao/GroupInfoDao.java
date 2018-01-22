package com.xaut.dao;

import com.xaut.entity.GroupInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(GroupInfo record);

    GroupInfo selectByPrimaryKey(Integer id);

    List<GroupInfo> selectAll();

    int updateByPrimaryKey(GroupInfo record);
}