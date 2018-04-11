package com.xaut.dao;

import com.xaut.entity.PlaceList;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PlaceListDao {
    int deleteByPrimaryKey(Integer id);

    int insert(PlaceList record);

    List<PlaceList> selectByPlaceId(Integer placeId);

    PlaceList selectByGameUid(String gameUid);

    List<PlaceList> selectAll();

    int updateByPrimaryKey(PlaceList record);
}