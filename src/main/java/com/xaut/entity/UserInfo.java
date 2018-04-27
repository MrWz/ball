package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo implements GetRedisKey{

    private Integer id;

    private String uid;

    private String name;

    private String password;

    private String picture;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

    public UserInfo(String name){
        this.name= name;
    }
    public UserInfo(String uid,String name,String password,String picture,Boolean deleted,Date creteTime,Date updateTime){
        this.uid = uid;
        this.name =name;
        this.password = password;
        this.picture = picture;
        this.deleted = deleted;
        this.createTime = creteTime;
        this.updateTime = updateTime;
    }

}