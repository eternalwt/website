package com.greengiant.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.Role;

import java.util.List;

public interface RoleService extends IService<Role> {
    String getRole(String username);

    void addRole(Role role);

    List<Role> getRoleListByUserName(String userName);

}
