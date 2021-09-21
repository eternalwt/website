package com.greengiant.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.dto.MenuTreeNode;
import com.greengiant.website.pojo.model.Menu;

import java.util.List;

public interface MenuService extends IService<Menu> {

    List<MenuTreeNode> getMenuTree();

    List<Menu> getMenuListByRole(String roleId);
}
