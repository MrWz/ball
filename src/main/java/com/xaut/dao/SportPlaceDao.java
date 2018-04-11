package com.xaut.dao;

import com.xaut.entity.SportPlace;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportPlaceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SportPlace record);

    SportPlace selectByPrimaryKey(Integer id);

    List<SportPlace> selectAll();

    int updateByPrimaryKey(SportPlace record);

    SportPlace selectByPlace(@Param(value = "type") String type, @Param(value = "identifier") String identifier);

}