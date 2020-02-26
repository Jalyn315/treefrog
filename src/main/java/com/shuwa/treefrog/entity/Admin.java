package com.shuwa.treefrog.entity;

import org.springframework.stereotype.Component;

/**
 * 管理员实体
 */
@Component
public class Admin {
    Integer idAdmin; //管理员 id
    String userNameAdmin; //管理员用户名
    String passwordAdmin; // 管理员密码

    public Admin() {
    }

    public Integer getIdAdmin() {
        return idAdmin;
    }

    public void setIdAdmin(Integer idAdmin) {
        this.idAdmin = idAdmin;
    }

    public String getUserNameAdmin() {
        return userNameAdmin;
    }

    public void setUserNameAdmin(String userNameAdmin) {
        this.userNameAdmin = userNameAdmin;
    }

    public String getPasswordAdmin() {
        return passwordAdmin;
    }

    public void setPasswordAdmin(String passwordAdmin) {
        this.passwordAdmin = passwordAdmin;
    }

    @Override
    public String toString() {
        return "admin{" +
                "idAdmin=" + idAdmin +
                ", userNameAdmin='" + userNameAdmin + '\'' +
                ", passwordAdmin='" + passwordAdmin + '\'' +
                '}';
    }
}
