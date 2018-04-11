package com.xaut.dao;

import com.xaut.entity.GameInfo;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GameInfoDao {
    int deleteByPrimaryKey(Integer id);

    boolean insert(GameInfo record);

    GameInfo selectByUid(String uid);

    List<GameInfo> selectAll();

    int updateByPrimaryKey(GameInfo record);
}