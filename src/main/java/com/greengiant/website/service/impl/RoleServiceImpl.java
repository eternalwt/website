package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.RoleMapper;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public String getRole(String username) {
        //根据权限，指定返回数据
        Role role = roleMapper.selectByName(username);
        if (role != null) {
            //todo 重构
            if ("user".equals(role.getRoleName())) {
                return "欢迎登陆";
            }
            if ("admin".equals(role.getRoleName())) {
                return "欢迎来到管理员页面";
            }
        }
        return "尚未分配角色";
    }

    @Override
    public void addRole(Role role) {
        roleMapper.insert(role);
    }

}
