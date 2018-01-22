package com.xaut.dao;

import com.xaut.entity.LabelInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabelInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(LabelInfo record);

    LabelInfo selectByPrimaryKey(Integer id);

    List<LabelInfo> selectAll();

    int updateByPrimaryKey(LabelInfo record);
}