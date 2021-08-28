package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.pojo.query.PageQuery;
import com.greengiant.website.service.RoleService;
import org.apache.shiro.SecurityUtils;
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

    @GetMapping(value = "/getById")
    public ResultBean getById(@RequestParam Long id) {
        SecurityUtils.getSubject().checkRole("aaa");
        return ResultUtils.success(roleService.getById(id));
    }

    @PostMapping(value="/list")
    public ResultBean getPageList(@RequestBody PageQuery<Role> pageQuery) {
//        IPage<Role> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
//        QueryWrapper<Role> wrapper = new QueryWrapper<>();
        IPage<Role> result = roleService.getPageList(pageQuery);

        return ResultUtils.success(result);
    }

    @GetMapping(value="/alllist")
    public ResultBean getAllRoleList() {
        return ResultUtils.success(roleService.list());
    }

    @PostMapping(value = "/edit")
    public ResultBean editRole(@RequestBody Role role) {
        return ResultUtils.success(roleService.editRole(role));
    }

    @PostMapping(value = "/delete")
    public ResultBean delRole(@RequestParam Long roleId) {
        return ResultUtils.success(roleService.delRole(roleId));
    }

    @GetMapping(value = "/hasRole")
    public ResultBean hasRole(@RequestParam Long roleId) {
        boolean result = SecurityUtils.getSubject().hasRole("aaa");
        return ResultUtils.success(result);
    }

}
