package com.xaut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Author : wangzhe
 * Date : on 2017/12/15
 * Description :
 * Version :
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TokenModel {
    // 用户 id
    private String userId;
    // 随机生成的 uuid
    private String token;

    @Override
    public String toString() {
        return userId + "_" + token;
    }
}
