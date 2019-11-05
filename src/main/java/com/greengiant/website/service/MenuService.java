package com.greengiant.website.service;

import com.greengiant.website.pojo.model.Menu;

import java.util.List;

public interface MenuService {

    List<Menu> selectByRole(String roleStr);

    List<Menu> selectByUserId(long userId);
}
