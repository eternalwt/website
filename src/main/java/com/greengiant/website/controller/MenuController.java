package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private RoleService roleService;



//    @GetMapping(value = "/getPermissionListByUserId")
//    public ResultBean getPermissionListByUserId(@RequestParam("roleId") Long roleId) {
//        QueryWrapper<Menu> menuWrapper = new QueryWrapper<>();
//        menuWrapper.like("role", roleId);
//
//        return ResultUtils.success(menuService.list(menuWrapper));
//    }

//    @GetMapping(value = "/getRolePermissionListMap")
//    public ResultBean getRolePermissionListMap() {
//        return ResultUtils.success(menuService.getRolePermissionListMap());
//    }
//
//    @PostMapping(value = "/updatePermission")
//    public ResultBean updatePermission(@RequestBody List<Map<String, String>> menuList) {
//        if (menuList != null && !menuList.isEmpty()) {
//            for (Map<String, String> menu : menuList) {
//                Role role = roleService.selectByName(menu.get("roleName"));
//                if (role != null) {
//                    menuService.updateRole(menu.get("perm"), Boolean.getBoolean(menu.get("checked")), role.getId().toString());
//                }
//            }
//        }
//
//        return ResultUtils.success();
//    }

    @PostMapping(value = "/add")
    public ResultBean addMenu(@RequestBody Menu menu) {
        return ResultUtils.success(menuService.save(menu));
    }

    @GetMapping(value = "/selectByUserId")
    public ResultBean selectByUserId(@RequestParam long userId){
        return ResultUtils.success(menuService.selectByUserId(userId));// todo 这个方法也是一个垃圾方法，在最新的设计下需要干掉
    }

    @PostMapping(value = "/list")
    public ResultBean getMenuList() {
        return ResultUtils.success(menuService.list());
    }

    @GetMapping(value = "/getMenuTree")
    public ResultBean getMenuTree() {
        return ResultUtils.success(menuService.getMenuTree());
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

}
