package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.ResultUtils;
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
    // todo 功能点：
    //  3.各类resource查询列表：到各自的原始表里面查询；
    //  4.能够勾选出哪些是自己角色有权限的(根据entity和resource查询permission)；
    //  5.能够添加或者去掉某些权限(根据entity和resource删除permission)
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

        List<RolePermission> rolePermList = new ArrayList<>();
        for (Long permId : authorizeQuery.getPermIdList()) {
            rolePermList.add(new RolePermission(authorizeQuery.getRoleId(), permId));
        }

        return ResultUtils.success(rolePermissionService.saveBatch(rolePermList));
    }

//    getRolePermList()

//        updateRolePerm

}
