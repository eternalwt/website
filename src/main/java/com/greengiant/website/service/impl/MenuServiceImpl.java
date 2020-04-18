package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.MenuMapper;
import com.greengiant.website.dao.UserRoleMapper;
import com.greengiant.website.pojo.dto.MenuTreeNode;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.RoleService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

    @Autowired
    private RoleService roleService;

    @Override
    public List<Menu> selectByRole(String roleStr) {
        return menuMapper.selectByRole(roleStr);
    }

    @Override
    public List<Menu> selectByUserId(long userId) {
        List<Menu> menuList = new ArrayList<>();
        List<UserRole> userRoleList = userRoleMapper.selectByUserId(userId);
        if (userRoleList != null && !userRoleList.isEmpty()) {
            for (int i = 0; i < userRoleList.size(); i++) {
                menuList.addAll(this.selectByRole(String.valueOf(userRoleList.get(0).getRoleId())));
            }
        }

        // 去重，排序，判空
        if (!menuList.isEmpty()) {
            menuList = menuList.stream()
                    .collect(Collectors.collectingAndThen(Collectors.toCollection(() -> new TreeSet<>(Comparator.comparing(Menu::getId))), ArrayList::new));
            menuList = menuList.stream().sorted(Comparator.comparing(Menu::getSort)).collect(Collectors.toList());// todo 这段代码可以写都更简洁
        }

        return menuList;
    }

    @Override
    public int updateRole(String menuName, boolean checked, String roleId) {
        if (checked) {
            return menuMapper.updateRoleAdd(menuName, roleId);
        }
        else {
            return menuMapper.updateRoleDel(menuName, roleId);
        }
    }

    @Override
    public List<MenuTreeNode> getMenuTree() {
        List<MenuTreeNode> nodeList = new ArrayList<>();

        List<Menu> menuList = menuMapper.selectAll();
        // 1.插入第一级节点
        if (menuList != null && !menuList.isEmpty()) {
            List<Menu> parentList = menuList.stream().filter(item -> item.getParentId() == null).collect(Collectors.toList());
            if (parentList != null && !parentList.isEmpty()) {
                for (Menu menu : parentList) {
                    MenuTreeNode node = new MenuTreeNode(menu.getId(), menu.getMenuName(), menu.getIcon(), menu.getUrl());
                    nodeList.add(node);
                }
            }
        }

//        int i = 0;
//        while (i < nodeList.size()) {
//            final int index = i;
//            List<Menu> list = menuList.stream().filter(item -> item.getParentId() != null && item.getParentId().equals(nodeList.get(index).getId())).collect(Collectors.toList());
//            // todo filter之后需要2次判断吗？
//            if (list != null && !list.isEmpty()) {
//                for (Menu menu : list) {
//                    MenuTreeNode node = new MenuTreeNode(menu.getId(), menu.getMenuName(), menu.getUrl());
//                    nodeList.get(i).getChildren().add(node);
//                }
//            }
//
//            i++;
//        }
        traverseList(nodeList, menuList);

        return nodeList;
    }

    private void traverseList(List<MenuTreeNode> nodeList, List<Menu> menuList) {
        for (int i = 0; i < nodeList.size(); i++) {
            final int index = i;
            List<Menu> list = menuList.stream().filter(item -> item.getParentId() != null && item.getParentId().equals(nodeList.get(index).getId())).collect(Collectors.toList());
            // todo filter之后需要2次判断吗？
            if (list != null && !list.isEmpty()) {
                for (Menu menu : list) {
                    MenuTreeNode node = new MenuTreeNode(menu.getId(), menu.getMenuName(), menu.getIcon(), menu.getUrl());
                    nodeList.get(i).getChildren().add(node);
                }
            }
            traverseList(nodeList.get(i).getChildren(), menuList);
        }
    }


    @Override
    public Map<String, List<String>> getRolePermissionListMap() {
        // todo 保证顺序（这个问题遇到过）
        Map<String, List<String>> rolePermMap = new HashedMap();
        // todo sql初始化脚本里面加入admin初始化赋权限
        List<Role> roleList =  roleService.list();
        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                List<String> permList = new ArrayList<>();
                rolePermMap.put(role.getRoleName(), permList);
            }
        }

        List<Menu> menuList = this.list();
        if (roleList != null && !roleList.isEmpty() && menuList != null && !menuList.isEmpty()) {
            for (Menu menu : menuList) {
                String roleStr = menu.getRole();
                if (roleStr != null) {
                    String[] roleIdList = roleStr.split(",");
                    for (int i = 0; i < roleIdList.length; i++) {
                        if (roleIdList[i] != null && !roleIdList[i].isEmpty()) {
                            String roleName = this.getRoleName(roleIdList[i], roleList);
                            rolePermMap.get(roleName).add(menu.getMenuName());
                        }
                    }
                }
            }
        }

        return rolePermMap;
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
