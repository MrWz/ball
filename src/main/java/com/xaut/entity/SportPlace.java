package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SportPlace {
    private Integer id;

    private String type;

    private String identifier;

    private Date startTime;

    private Date endTime;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}