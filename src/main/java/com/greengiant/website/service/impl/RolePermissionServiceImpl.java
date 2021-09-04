package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.RolePermissionMapper;
import com.greengiant.website.pojo.model.RolePermission;
import com.greengiant.website.service.RolePermissionService;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}
