package com.greengiant.website.service;

import com.greengiant.website.dao.RoleDao;
import com.greengiant.website.pojo.model.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public String getRole(String username) {
        //根据权限，指定返回数据
        Role role = roleDao.selectByName(username);
        if (role != null) {
            //todo 重构
            if ("user".equals(role.getRoleName())) {
                return "欢迎登陆";
            }
            if ("admin".equals(role.getRoleName())) {
                return "欢迎来到管理员页面";
            }
        }
        return "获取角色信息失败";
    }

    @Override
    public void addRole(Role role) {
        roleDao.insert(role);
    }

}
