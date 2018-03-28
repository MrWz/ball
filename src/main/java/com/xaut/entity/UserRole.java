package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRole {

    private Integer id;

    private String userUid;

    private String roleUid;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

    public UserRole(String userUid,String roleUid,Boolean deleted,Date creteTime,Date updateTime){
        this.userUid = userUid;
        this.roleUid = roleUid;
        this.deleted = deleted;
        this.createTime = creteTime;
        this.updateTime = updateTime;
    }
}