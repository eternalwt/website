package com.greengiant.website.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.greengiant.website.dao.MenuMapper;
import com.greengiant.website.dao.UserRoleMapper;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
}
