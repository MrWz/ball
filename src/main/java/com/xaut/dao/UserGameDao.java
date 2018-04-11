package com.xaut.dao;

import com.xaut.entity.UserGame;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserGameDao {
    int deleteByPrimaryKey(Integer id);

    int insert(UserGame record);

    UserGame selectByPrimaryKey(Integer id);

    List<UserGame> selectAll();

    int updateByPrimaryKey(UserGame record);

    List<UserGame> selectByGameUid(String gameUid);

    List<UserGame> selectByUserUid(String userUid);

}