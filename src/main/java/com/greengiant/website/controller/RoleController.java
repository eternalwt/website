package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/add")
    public void addRole(Role role) {
        //todo 修改返回值类型
        //roleService.addRole(role);
    }

    @GetMapping(value="/list")
    public ResultBean getRoleList() {
        // todo return List<Role>;
        //roleService.
        return null;
    }

    @PostMapping(value = "/edit")
    public void editRole(Role role) {
        //todo editRole
    }

    @PostMapping(value = "/delete")
    public void delRole(Role role) {
        //todo delete
    }

}
