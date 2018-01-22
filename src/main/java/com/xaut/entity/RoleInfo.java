package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RoleInfo {

    private Integer id;

    private String uid;

    private String description;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}