package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.enums.StatusCodeEnum;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Perm;
import com.greengiant.website.pojo.query.AuthorizeQuery;
import com.greengiant.website.service.PermService;
import com.greengiant.website.service.RolePermissionService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/perm")
public class PermController {

    @Autowired
    private PermService permService;

    @Autowired
    private RolePermissionService rolePermissionService;

    @GetMapping(value = "/isPermitted")
    public ResultBean checkPermission(@RequestParam String permission) {
        // todo 加缓存 跟缓存相关的2个问题：1.能否用注解；2.切换缓存是否有问题
        Subject subject = SecurityUtils.getSubject();
        return ResultUtils.success(subject.isPermitted(permission));
    }

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
