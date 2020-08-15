package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greengiant.website.dao.RoleMapper;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.RoleService;
import com.greengiant.website.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    // todo 有了menu表，permission表怎么处理？关系始终没理顺
    // todo 文件里面的很多方法是需要加缓存的，配合CustomRealm里面的缓存读取

    @Autowired
    private RoleService roleService;

    @GetMapping(value = "/isPermitted")
    public ResultBean checkPermission(@RequestParam String permission) {
        // todo 加缓存
        /**
         * 跟缓存相关的2个问题：1.能否用注解；2.切换缓存是否有问题
         */
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
        QueryWrapper<Menu> menuWrapper = new QueryWrapper<>();
        menuWrapper.like("role", roleId);

        return ResultUtils.success(menuService.list(menuWrapper));
    }

    @GetMapping(value = "/getRolePermissionListMap")
    public ResultBean getRolePermissionListMap() {
        return ResultUtils.success(menuService.getRolePermissionListMap());
    }

    @PostMapping(value = "/updatePermission")
    public ResultBean updatePermission(@RequestBody List<Map<String, String>> menuList) {
        if (menuList != null && !menuList.isEmpty()) {
            for (Map<String, String> menu : menuList) {
                Role role = roleService.selectByName(menu.get("roleName"));
                if (role != null) {
                    menuService.updateRole(menu.get("perm"), Boolean.getBoolean(menu.get("checked")), role.getId().toString());
                }
            }
        }

        return ResultUtils.success();
    }

    @PostMapping(value = "/add")
    public ResultBean addMenu(@RequestBody Menu menu) {
        return ResultUtils.success(menuService.save(menu));
    }

    @GetMapping(value = "/selectByUserId")
    public ResultBean selectByUserId(@RequestParam long userId){
        return ResultUtils.success(menuService.selectByUserId(userId));
    }

    @PostMapping(value = "/list")
    public ResultBean getMenuList() {
        // todo
        return null;
    }

    @PostMapping(value = "/edit")
    public ResultBean editMenu() {
        // todo
        return null;
    }

    @PostMapping(value = "/delete")
    public ResultBean delMenu() {
        // todo
        return null;
    }

    @GetMapping(value = "/getMenuTree")
    public ResultBean getMenuTree() {
        return ResultUtils.success(menuService.getMenuTree());
    }

}
