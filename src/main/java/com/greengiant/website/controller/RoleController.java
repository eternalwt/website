package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public void addRole(Role role) {
        //todo 修改返回值类型
        //roleService.addRole(role);
    }

    @RequestMapping(value="/list", method = RequestMethod.GET)
    public ResultBean getRoleList() {
        // todo return List<Role>;
        //roleService.
        return null;
    }

    //todo editRole

    //todo delete

}
