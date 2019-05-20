package com.greengiant.website.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController{

    // admin路径

    //todo addUser editUser delUser

    @GetMapping(value = "/getMessage")
    public String getMessage() {
        return "您拥有用户权限，可以获得该接口的信息！";
    }
}

