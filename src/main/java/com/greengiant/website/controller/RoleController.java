package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greengiant.website.pojo.PageParam;
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
    public ResultBean addRole(@RequestBody Role role) {
        return ResultUtils.success(roleService.save(role));
    }

    @PostMapping(value="/list")
    public ResultBean getRoleListByPage(@RequestBody PageParam pageParam) {// todo search
        IPage<Role> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        IPage<Role> result = roleService.page(page, wrapper);

        return ResultUtils.success(result);
    }

    @GetMapping(value="/alllist")
    public ResultBean getAllRoleList() {
        return ResultUtils.success(roleService.list());
    }

    @PostMapping(value = "/edit")
    public void editRole(@RequestBody Role role) {
        //todo editRole
    }

    @PostMapping(value = "/delete")
    public ResultBean delRole(@RequestParam Long roleId) {
        roleService.delRole(roleId);
        return ResultUtils.success();
    }

}
