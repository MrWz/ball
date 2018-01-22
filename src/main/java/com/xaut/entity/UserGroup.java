package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserGroup {
    private Integer id;

    private String userUid;

    private String groupUid;

    private Boolean leader;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

}