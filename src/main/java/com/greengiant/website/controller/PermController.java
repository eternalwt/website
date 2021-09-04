package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.enums.EntityTypeEnum;
import com.greengiant.website.enums.StatusCodeEnum;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Perm;
import com.greengiant.website.pojo.model.RolePermission;
import com.greengiant.website.pojo.query.AuthorizeQuery;
import com.greengiant.website.service.PermService;
import com.greengiant.website.service.RolePermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/perm")
public class PermController {

    @Autowired
    private PermService permService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping(value = "/check")
    public ResultBean check(@RequestParam("perm") String perm) {
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("admin::aaa");// todo 先写通一个

        // todo
        return ResultUtils.success("登出成功！");
    }

    //  1.添加资源；
    //  2.给角色授权
    //  4.能够勾选出哪些是自己角色有权限的(根据entity和resource查询permission)；
    //  5.能够添加或者去掉某些权限(根据entity和resource删除permission)
    // todo 功能点：
    //  3.各类resource查询列表：到各自的原始表里面查询【检查一下menu有没有获取所有的方法，如果没有则加上】
    //  6.测试

    @PostMapping(value = "/addBatch")
    public ResultBean addBatch(@RequestBody List<Perm> permList) {
        if (permList.isEmpty()) {
            return ResultUtils.fail(StatusCodeEnum.PARAM_ERROR.getCode(), StatusCodeEnum.PARAM_ERROR.getMsg());
        }

        return ResultUtils.success(permService.saveBatch(permList));
    }

    @PostMapping(value = "/addRolePerm") // authorize
    public ResultBean addRolePerm(@RequestBody AuthorizeQuery authorizeQuery) {
        if (authorizeQuery.getRoleId() == null || authorizeQuery.getPermIdList() == null || authorizeQuery.getPermIdList().isEmpty()) {
            return ResultUtils.fail(StatusCodeEnum.PARAM_ERROR.getCode(), StatusCodeEnum.PARAM_ERROR.getMsg());
        }

        return ResultUtils.success(rolePermissionService.addRolePermBatch(authorizeQuery));
    }

    @PostMapping(value = "/getPermList")
    public ResultBean getPermList(@RequestBody Perm perm) {
        return ResultUtils.success(permService.getPermList(perm));
    }

    @PostMapping(value = "/updateRolePerm")
    public ResultBean updateRolePerm( AuthorizeQuery authorizeQuery) {
        // 1.先整体删除老的
        AuthorizeQuery old = new AuthorizeQuery();
        old.setRoleId(authorizeQuery.getRoleId());
        rolePermissionService.delRolePermBatch(old);
        // 2.再整体添加新的
        return ResultUtils.success(rolePermissionService.addRolePermBatch(authorizeQuery));
    }

}
