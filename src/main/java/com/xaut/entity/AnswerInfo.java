package com.xaut.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnswerInfo {
    private Integer id;

    private Integer postId;

    private String userUid;

    private Integer handNum;

    private Integer footNum;

    private Boolean deleted;

    private Date createTime;

    private Date updateTime;

    private String answerContext;
}