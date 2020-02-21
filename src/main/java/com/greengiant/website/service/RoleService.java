package com.greengiant.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.Role;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> getRoleListByUserName(String userName);

    int editRole(Role role);

    void delRole(Long roleId);
}
