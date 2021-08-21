package com.greengiant.website.pojo.query;

import java.util.List;

public class UserQuery {
    private String userName;
    private String password;
    private List<Long> roleIdList;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Long> getRoleIdList() {
        return roleIdList;
    }

    public void setRoleIdList(List<Long> roleIdList) {
        this.roleIdList = roleIdList;
    }

    @Override
    public String toString() {
        return "AddUserQuery{" +
                "userName='" + userName + '\'' +
                ", password='" + password + '\'' +
                ", roleIdList=" + roleIdList +
                '}';
    }
}
