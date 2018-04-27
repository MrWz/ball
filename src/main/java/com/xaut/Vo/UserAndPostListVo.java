package com.xaut.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author ： Wangzhe
 * description :将用户和帖子关联起来
 * version : 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAndPostListVo {
    int id;
    String userUid;
    String title;
    int num;
    String content;

    String name;
    String picture;
}
