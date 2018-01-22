package com.xaut.dao;

import com.xaut.entity.UserRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRecordDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRecord record);

    UserRecord selectByPrimaryKey(Integer id);

    List<UserRecord> selectAll();

    int updateByPrimaryKey(UserRecord record);
}