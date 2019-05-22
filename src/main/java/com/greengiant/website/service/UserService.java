package com.greengiant.website.service;

import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.vo.AddUserVo;

public interface UserService {
    //todo 这里也用vo，从设计上有没有问题？

    void addUser(AddUserVo userVo);

    User getByName(String userName);

}
