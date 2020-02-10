package com.greengiant.website.controller;

import com.greengiant.website.dao.RoleMapper;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.PermService;
import com.greengiant.website.utils.ResultUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    // todo 默认的menu（init.sql脚本）
    // todo 有了menu表，permission表怎么处理？关系始终没理顺

    @Autowired
    private PermService permService;// todo 这个service有坏味道，看看要不要干掉

    @Autowired
    private MenuService menuService;

//    @Autowired
//    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

//    @RequiresRoles("admin")
//    @RequiresPermissions("aaa")
    @GetMapping(value = "/isPermitted")
    public ResultBean checkPermission() {
        Subject subject = SecurityUtils.getSubject();
        subject.hasRole("admin");
        boolean result = subject.isPermitted("admin");
        // todo 这个方法测完可以干掉。这里为啥principal为空？是不是没传session等过来？
        return ResultUtils.success();
    }

    @GetMapping(value = "/getAllPermissionList")
    public ResultBean getAllPermissionList() {
        // todo 把返回值的异常处理加一下
        return ResultUtils.success(menuService.list());
    }

    // todo restful改造
    @GetMapping(value = "/getPermissionListByUserId")
    public ResultBean getPermissionListByUserId(@RequestParam("roleId") Long roleId) {
        Map<String, Object> queryMap = new HashedMap();
        queryMap.put("role", roleId);// todo 这里应该用in
        return ResultUtils.success(menuService.listByMap(queryMap));
    }

    @GetMapping(value = "/getRolePermissionListMap")
    public ResultBean getRolePermissionListMap() {
        return ResultUtils.success(permService.getRolePermissionListMap());
    }

    @PostMapping(value = "/updatePermission")
    public ResultBean updatePermission(@RequestBody List<Map<String, String>> menuList) {
        if (menuList != null && !menuList.isEmpty()) {
            for (Map<String, String> menu : menuList) {
                // todo 用不用wrapper也要统一
                Role role = roleMapper.selectByName(menu.get("roleName"));
                if (role != null) {
                    menuService.updateRole(menu.get("perm"), Boolean.valueOf(menu.get("checked")), role.getId().toString());
                }
            }
        }

        return ResultUtils.success();
    }

}
