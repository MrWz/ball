package com.xaut.dao;

import com.xaut.entity.TypeInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(TypeInfo record);

    TypeInfo selectByPrimaryKey(Integer id);

    List<TypeInfo> selectAll();

    int updateByPrimaryKey(TypeInfo record);
}