package com.greengiant.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.query.UserQuery;

public interface UserService extends IService<User> {

    void addUser(UserQuery userQuery);

    void delUser(Long userId);

    User getByName(String userName);

    int changePassword(User user, String rawPassword);

}
