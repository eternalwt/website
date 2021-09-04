package com.greengiant.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.dto.MenuTreeNode;
import com.greengiant.website.pojo.model.Menu;

import java.util.List;
import java.util.Map;

public interface MenuService extends IService<Menu> {

    List<Menu> selectByRole(String roleStr);

    List<Menu> selectByUserId(long userId);

    List<MenuTreeNode> getMenuTree();

//    Map<String, List<String>> getRolePermissionListMap();

}
