package com.xaut.dao;

import com.xaut.entity.SportPlace;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SportPlaceDao {
    int deleteByPrimaryKey(Integer id);

    int insert(SportPlace record);

    SportPlace selectByPrimaryKey(Integer id);

    List<SportPlace> selectAll();

    int updateByPrimaryKey(SportPlace record);
}