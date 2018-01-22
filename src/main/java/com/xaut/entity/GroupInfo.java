package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GroupInfo {
    private Integer id;

    private String uid;

    private String name;

    private String image;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

}