package com.greengiant.website.service.impl;

import com.greengiant.website.dao.MenuMapper;
import com.greengiant.website.dao.UserRoleDao;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuMapper menuDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public List<Menu> selectByRole(String roleStr) {
        return menuDao.selectByRole(roleStr);
    }

    @Override
    public List<Menu> selectByUserId(long userId) {
        List<Menu> menuList = new ArrayList<>();
        List<UserRole> userRoleList = userRoleDao.selectByUserId(userId);
        if (userRoleList != null && !userRoleList.isEmpty()) {
            for (int i = 0; i < userRoleList.size(); i++) {
                menuList.addAll(this.selectByRole(String.valueOf(userRoleList.get(0).getRoleId())));
            }
        }

        // todo menuList的去重和排序

        return menuList;
    }
}
