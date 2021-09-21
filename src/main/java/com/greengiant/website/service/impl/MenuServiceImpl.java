package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.MenuMapper;
import com.greengiant.website.pojo.dto.MenuTreeNode;
import com.greengiant.website.pojo.model.Menu;
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

    @Override
    public List<MenuTreeNode> getMenuTree() {
        List<MenuTreeNode> nodeList = new ArrayList<>();

        List<Menu> menuList = menuMapper.selectAll();
        // 1.插入第一级节点
        if (menuList != null && !menuList.isEmpty()) {
            List<Menu> parentList = menuList.stream().filter(item -> item.getParentId() == null).collect(Collectors.toList());
            if (!parentList.isEmpty()) {
                for (Menu menu : parentList) {
                    MenuTreeNode node = new MenuTreeNode(menu.getId(), menu.getMenuName(), menu.getIcon(), menu.getUrl());
                    nodeList.add(node);
                }
            }
        }

        traverseList(nodeList, menuList);

        return nodeList;
    }

    private void traverseList(List<MenuTreeNode> nodeList, List<Menu> menuList) {
        if (nodeList == null || menuList == null) {
            return;
        }

        for (int i = 0; i < nodeList.size(); i++) {
            final int index = i;
            List<Menu> list = menuList.stream().filter(item -> item.getParentId() != null && item.getParentId().equals(nodeList.get(index).getId())).collect(Collectors.toList());
            if (!list.isEmpty()) {
                for (Menu menu : list) {
                    MenuTreeNode node = new MenuTreeNode(menu.getId(), menu.getMenuName(), menu.getIcon(), menu.getUrl());
                    nodeList.get(i).getChildren().add(node);
                }
            }
            traverseList(nodeList.get(i).getChildren(), menuList);
        }
    }

    @Override
    public List<Menu> getMenuListByRole(String roleId) {// todo resource是否有必要单独搞一个controller？
        // todo 根据资源类别过滤
        // 1.获取权限，然后根据权限过滤，要么就直接用一个语句搞定。先写通再看

        return null;
    }

}
