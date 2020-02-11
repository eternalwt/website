package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.MenuMapper;
import com.greengiant.website.dao.UserRoleMapper;
import com.greengiant.website.pojo.dto.MenuTreeNode;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private MenuMapper menuMapper;

    @Autowired
    private UserRoleMapper userRoleMapper;

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

        // todo menuList的去重和排序

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
                    MenuTreeNode node = new MenuTreeNode(menu.getId(), menu.getMenuName(), menu.getUrl());
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
                    MenuTreeNode node = new MenuTreeNode(menu.getId(), menu.getMenuName(), menu.getUrl());
                    nodeList.get(i).getChildren().add(node);
                }
            }
            traverseList(nodeList.get(i).getChildren(), menuList);
        }
    }

}
