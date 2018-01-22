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
        System.out.println(catchToken);
        return tokenModel;
    }
    public Boolean checkToken(String token){
        if(StringUtils.equals(token,catchToken)){
            return true;
        }else {
            return false;
        }
    }
}
