package com.xaut.manager;

import com.xaut.dto.TokenModel;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * Author : wangzhe
 * Date : on 2017/12/15
 * Description :
 * Version :
 */
@Component
public class TokenManager {
    private static String catchToken = "default_token";
    public TokenModel createToken(String name,String pwd){
        TokenModel tokenModel=new TokenModel(name,pwd);
        catchToken=tokenModel.toString();
        return tokenModel;
    }
    public Boolean checkToken(String token){
        if(StringUtils.equals(token,catchToken)){
            return true;
        }else {
            return false;
        }
    }

    public TokenModel getToken(String authentication) {
        if (authentication == null || authentication.length() == 0) {
            return null;
        }
        String[] param=authentication.split("_");
        if (param.length != 2) {
            return null;
        }
        // 使用 userId 和源 token 简单拼接成的 token，可以增加加密措施
        String userId=param[0];
        String token=param[1];
        return new TokenModel(userId, token);
    }

    public void deleteToken(String token){
        catchToken = "default_token";
    }
}
