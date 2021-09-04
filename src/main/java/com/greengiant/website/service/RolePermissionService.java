package com.greengiant.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.RolePermission;
import com.greengiant.website.pojo.query.AuthorizeQuery;

public interface RolePermissionService extends IService<RolePermission> {

    boolean addRolePermBatch(AuthorizeQuery authorizeQuery);

    boolean delRolePermBatch(AuthorizeQuery authorizeQuery);

}
