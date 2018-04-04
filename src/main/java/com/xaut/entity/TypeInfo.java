package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TypeInfo {
    private Integer id;

    private String type;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;
}