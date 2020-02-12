package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.service.RoleService;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    @PostMapping(value = "/add")
    public ResultBean addRole(Role role) {
        return ResultUtils.success(roleService.save(role));
    }

    @GetMapping(value="/list")
    public ResultBean getRoleList() {
        // todo return List<Role>;
        return ResultUtils.success(roleService.list());
    }

    @PostMapping(value = "/edit")
    public void editRole(Role role) {
        //todo editRole
    }

    @PostMapping(value = "/delete")
    public ResultBean delRole(Long roleId) {
        return ResultUtils.success(roleService.removeById(roleId));
    }

}
