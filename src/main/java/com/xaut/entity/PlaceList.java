package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PlaceList {
    private Integer id;

    private String gameUid;

    private Integer placeId;

    private Date startTime;

    private Date endTime;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}