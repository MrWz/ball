package com.xaut.util;

/**
 * Created by cvter on 2017/5/23.
 */
public enum RoleUtil {
    ROLE_1("管理员"),
    ROLE_2("普通用户");

    public String getRole() {
        return role;
    }

    private String role;
    RoleUtil(String role){
        this.role=role;
    }
}
