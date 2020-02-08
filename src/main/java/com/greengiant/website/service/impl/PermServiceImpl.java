package com.greengiant.website.service.impl;

import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.PermService;
import com.greengiant.website.service.RoleService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class PermServiceImpl implements PermService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Override
    public Map<String, List<String>> getRolePermissionListMap() {
        // todo 保证顺序（这个问题遇到过）
        Map<String, List<String>> rolePermMap = new HashedMap();
        // todo sql初始化脚本里面加入admin初始化赋权限
        List<Role> roleList =  roleService.list();
        List<Menu> menuList = menuService.list();
        if (roleList != null && !roleList.isEmpty() && menuList != null && !menuList.isEmpty()) {
            for (Menu menu : menuList) {
                String roleStr = menu.getRole();
                if (roleStr != null) {
                    String[] roleIdList = roleStr.split(",");
                    for (int i = 0; i < roleIdList.length; i++) {
                        if (roleIdList[i] != null && !roleIdList[i].isEmpty()) {
                            String roleName = this.getRoleName(roleIdList[i], roleList);
                            if (rolePermMap.containsKey(roleName)) {
                                rolePermMap.get(roleName).add(menu.getMenuName());
                            }
                            else {
                                List<String> permList = new ArrayList<>();
                                permList.add(menu.getMenuName());
                                rolePermMap.put(roleName, permList);
                            }
                        }
                    }
                }
            }
        }

        return rolePermMap;
    }

    // todo
    void updatePermissionBatch() {
        // todo 定接口

    }

    private String getRoleName(String roleId, List<Role> roleList) {
        String roleName = "";
        if (roleId != null) {
            for (Role role : roleList) {
                if (roleId.equals(role.getId().toString())) {
                    return role.getRoleName();
                }
            }
        }

        return roleName;
    }

}
