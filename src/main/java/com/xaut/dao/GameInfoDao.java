package com.xaut.dao;

import com.xaut.entity.GameInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameInfoDao {
    int deleteByPrimaryKey(Integer id);

    int insert(GameInfo record);

    GameInfo selectByPrimaryKey(Integer id);

    List<GameInfo> selectAll();

    int updateByPrimaryKey(GameInfo record);
}