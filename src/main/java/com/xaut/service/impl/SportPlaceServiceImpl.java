package com.xaut.service.impl;

import com.xaut.dao.SportPlaceDao;
import com.xaut.entity.SportPlace;
import com.xaut.service.SportPlaceService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Author ： wangzhe
 * Description : 场地信息
 * Version : 0.1
 */
@Service(value = "SportPlaceService")
@Slf4j
public class SportPlaceServiceImpl implements SportPlaceService {
    @Autowired
    SportPlaceDao sportPlaceDao;

    @Override
    public boolean save(SportPlace sportPlace) {
        sportPlace.setDeleted(false);
        Date date = new Date();
        sportPlace.setCreateTime(date);
        sportPlace.setUpdateTime(date);

        sportPlaceDao.insert(sportPlace);
        return true;
    }
}
