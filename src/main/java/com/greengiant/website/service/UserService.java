package com.greengiant.website.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.query.PageQuery;
import com.greengiant.website.pojo.query.UserQuery;

public interface UserService extends IService<User> {

    boolean addUser(UserQuery userQuery);

    boolean delUser(Long userId);

    User getByName(String userName);

    int changePassword(User user, String rawPassword);

    IPage<User> getPageList(PageQuery<User> pageQuery);
}
