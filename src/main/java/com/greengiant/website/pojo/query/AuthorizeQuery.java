package com.greengiant.website.pojo.query;

import java.util.List;

public class AuthorizeQuery {// todo 这个类是否需要改名？

    private Long roleId;

    // todo 资源类别是否在这里冗余一下？写完再说

    private List<Long> permIdList;

    public Long getRoleId() {
        return roleId;
    }

    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }

    public List<Long> getPermIdList() {
        return permIdList;
    }

    public void setPermIdList(List<Long> permIdList) {
        this.permIdList = permIdList;
    }

    @Override
    public String toString() {
        return "AuthorizeQuery{" +
                "roleId=" + roleId +
                ", permIdList=" + permIdList +
                '}';
    }
}
