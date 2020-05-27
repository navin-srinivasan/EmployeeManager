package com.zohocorp.employeeManager.model;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class UserBean {

    private String username;
    private String loginCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoginCode() {
        return loginCode;
    }

    public void setLoginCode(String loginCode) {
        this.loginCode = loginCode;
    }

    @Override
    public String toString() {
        return "UserBean [username=" + username + ", loginCode=" + loginCode + "]";
    }


}
