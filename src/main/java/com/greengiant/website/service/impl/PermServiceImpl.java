package com.greengiant.website.service.impl;

import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.PermService;
import com.greengiant.website.service.RoleService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PermServiceImpl implements PermService {

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    @Override
    public Map<String, List<String>> getRolePermissionListMap() {
        // todo 改善效率
        Map<String, List<String>> rolePermMap = new HashedMap();

        List<Role> roleList =  roleService.list();
        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                Map<String, Object> searchMap = new HashedMap();
                searchMap.put("role", role.getId());
                // todo 如果遇到异常怎么处理？感觉这段写的很垃圾
                List<Menu> menuList = (List<Menu>)menuService.listByMap(searchMap);
                if (menuList != null && !menuList.isEmpty()) {
                    rolePermMap.put(role.getRoleName(), menuList.stream().map(Menu::getMenuName).collect(Collectors.toList()));
                }
            }
        }

        return rolePermMap;
    }

}
