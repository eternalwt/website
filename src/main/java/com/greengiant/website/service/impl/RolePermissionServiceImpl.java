package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.RolePermissionMapper;
import com.greengiant.website.pojo.model.RolePermission;
import com.greengiant.website.pojo.query.AuthorizeQuery;
import com.greengiant.website.service.RolePermissionService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {

    @Override
    public boolean addRolePermBatch(AuthorizeQuery authorizeQuery) {
        List<RolePermission> rolePermList = new ArrayList<>();
        for (Long permId : authorizeQuery.getPermIdList()) {
            rolePermList.add(new RolePermission(authorizeQuery.getRoleId(), permId));
        }

        return this.saveBatch(rolePermList);
    }

    @Override
    public boolean delRolePermBatch(AuthorizeQuery authorizeQuery) {
        QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
        if (authorizeQuery.getRoleId() != null) {
            wrapper.eq("role_id", authorizeQuery.getRoleId());
        }
        if (authorizeQuery.getPermIdList() != null && !authorizeQuery.getPermIdList().isEmpty()) {
            wrapper.in("permission_id", authorizeQuery.getPermIdList());
        }

        return this.remove(wrapper);
    }

}
