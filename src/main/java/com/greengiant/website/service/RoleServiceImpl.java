package com.greengiant.website.service;

import com.greengiant.website.dao.UserRoleDao;
import com.greengiant.website.model.UserRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public String getRole(String username) {
        //根据权限，指定返回数据
        UserRole role = userRoleDao.getUserRoleByName(username);
        if (role != null) {
            //todo 重构
            if ("user".equals(role.getRolename())) {
                return "欢迎登陆";
            }
            if ("admin".equals(role.getRolename())) {
                return "欢迎来到管理员页面";
            }
        }
        return "获取角色信息失败";
    }
}
