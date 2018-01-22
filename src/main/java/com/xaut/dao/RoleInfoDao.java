package com.xaut.dao;

import com.xaut.entity.RoleInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfo record);

    RoleInfo selectByPrimaryKey(Integer id);

    List<RoleInfo> selectAll();

    int updateByPrimaryKey(RoleInfo record);
}