package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecordInfo {
    private Integer id;

    private String uid;

    private Integer num;

    private Integer score;

    private Integer assists;

    private Integer rebound;

    private Integer block;

    private Integer steal;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

}