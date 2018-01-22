package com.xaut.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Author ： rentao
 * Date : 17/11/13
 * Description  : 用户信息
 * Version : 0.1
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoDto implements Serializable {
    private String token;
}


