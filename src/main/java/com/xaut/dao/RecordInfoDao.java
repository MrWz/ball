package com.xaut.dao;

import com.xaut.entity.RecordInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecordInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(RecordInfo record);

    RecordInfo selectByPrimaryKey(Integer id);

    List<RecordInfo> selectAll();

    int updateByPrimaryKey(RecordInfo record);
}