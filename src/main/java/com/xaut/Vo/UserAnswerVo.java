package com.xaut.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * author ： Wangzhe
 * description :构造用户回复帖子返回值
 * version : 1.0
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnswerVo {
    int id;
    int postId;
    String userUid;
    int handNum;
    int footNum;
    String answerContext;

    String title;
}
