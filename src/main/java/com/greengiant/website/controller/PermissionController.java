package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greengiant.website.dao.RoleMapper;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.PermService;
import com.greengiant.website.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/permission")
public class PermissionController {

    // todo 菜单稳定一点后，添加默认的menu（init.sql脚本）
    // todo 有了menu表，permission表怎么处理？关系始终没理顺

    @Autowired
    private PermService permService;// todo 这个service有坏味道，看看要不要干掉

    @Autowired
    private MenuService menuService;

//    @Autowired
//    private RoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @GetMapping(value = "/isPermitted")
    public ResultBean checkPermission(String permission) {
        // todo 过滤一遍很多地方不需要userId，从subject里面获取
        // todo 加缓存
        Subject subject = SecurityUtils.getSubject();
        return ResultUtils.success(subject.isPermitted(permission));
    }

    @GetMapping(value = "/getAllPermissionList")
    public ResultBean getAllPermissionList() {
        // todo 把返回值的异常处理加一下
        return ResultUtils.success(menuService.list());
    }

    @GetMapping(value = "/getPermissionListByUserId")
    public ResultBean getPermissionListByUserId(@RequestParam("roleId") Long roleId) {
        QueryWrapper<Menu> menuWrapper = new QueryWrapper<Menu>();
        menuWrapper.like("role", roleId);

        return ResultUtils.success(menuService.list(menuWrapper));
    }

    @GetMapping(value = "/getRolePermissionListMap")
    public ResultBean getRolePermissionListMap() {
        return ResultUtils.success(permService.getRolePermissionListMap());
    }

    @PostMapping(value = "/updatePermission")
    public ResultBean updatePermission(@RequestBody List<Map<String, String>> menuList) {
        if (menuList != null && !menuList.isEmpty()) {
            for (Map<String, String> menu : menuList) {
                // todo 尝试用updateWrapper重构
                Role role = roleMapper.selectByName(menu.get("roleName"));
                if (role != null) {
                    menuService.updateRole(menu.get("perm"), Boolean.valueOf(menu.get("checked")), role.getId().toString());
                }
            }
        }

        return ResultUtils.success();
    }

}
