package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostInfo {
    private Integer id;

    private String userUid;

    private String title;

    private Integer num;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

    private String content;
}