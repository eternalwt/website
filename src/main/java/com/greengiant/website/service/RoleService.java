package com.greengiant.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.pojo.query.PageQuery;

import java.util.List;

public interface RoleService extends IService<Role> {

    List<Role> getRoleListByUserName(String userName);

    int editRole(Role role);

    boolean delRole(Long roleId);

    Role selectByName(String roleName);

    IPage<Role> getPageList(PageQuery<Role> pageQuery);
}
