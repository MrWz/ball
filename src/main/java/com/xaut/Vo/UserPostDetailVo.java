package com.xaut.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author ： Wangzhe
 * description :获取帖子详情对象
 * version : 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPostDetailVo {
    String uid;
    String name;
    String picture;

    int id;
    int postId;
    int handNum;
    int footNum;
    String answerContext;
}
