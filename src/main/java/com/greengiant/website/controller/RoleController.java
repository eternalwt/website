package com.greengiant.website.controller;

import com.greengiant.website.pojo.model.Role;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {


    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addRole(Role role) {
        //todo 修改返回值类型

    }

    //todo editRole

    //todo delete

}
