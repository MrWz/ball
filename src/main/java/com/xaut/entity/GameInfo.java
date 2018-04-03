package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameInfo {
    private Integer id;

    private String uid;

    private String userUid;

    private String type;

    private Date startTime;

    private String place;

    private Integer peopleNum;

    private String description;

    private Date releaseTime;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

}