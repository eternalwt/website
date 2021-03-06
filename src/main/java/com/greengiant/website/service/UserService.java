package com.greengiant.website.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.query.AddUserQuery;

public interface UserService extends IService<User> {
    //todo 这里也用vo，从设计上有没有问题？

    void addUser(AddUserQuery userVo);

    void delUser(Long userId);

    User getByName(String userName);

    int changePassword(User user, String rawPassword);

}
