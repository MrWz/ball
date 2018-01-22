package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo {

    private Integer id;

    private String uid;

    private String email;

    private String name;

    private String password;

    private String imagePath;

    private Date loginTime;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}