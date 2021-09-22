package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.enums.ResourceTypeEnum;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.Perm;
import com.greengiant.website.pojo.model.UserRole;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.PermService;
import com.greengiant.website.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/menu")
public class MenuController {

    // todo menu是资源，需要在资源的增删查改的地方联动Perm类

    @Autowired
    private MenuService menuService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private PermService permService;

    @PostMapping(value = "/add")
    public ResultBean addMenu(@RequestBody Menu menu) {
        return ResultUtils.success(menuService.save(menu));
    }

    @PostMapping(value = "/list")
    public ResultBean getMenuList() {
        return ResultUtils.success(menuService.list());
    }

    @GetMapping(value = "/getMenuListByRole")
    public ResultBean getMenuListByRole(@RequestParam("roleId") String roleId) {
        List<Menu> menuList = new ArrayList<>();

        // 1.根据perm表里面的entityId和resourceCode查询出所有菜单perm，取resourceInstanceId
        QueryWrapper permWrapper = new QueryWrapper<>();
        permWrapper.eq("entity_id", roleId);
        permWrapper.eq("resource_instance_id", ResourceTypeEnum.MENU.getCode());
        List<Perm> permList = permService.list(permWrapper);
        if (permList != null && !permList.isEmpty()) {
            // todo 2.根据菜单resourceInstanceId到menu表中取出菜单数据【补数据，然后确认授权哪里是OK的】
            QueryWrapper menuWrapper = new QueryWrapper();
            menuWrapper.in("id", permList.stream().map(Perm::getResourceInstanceId).collect(Collectors.toList()));
            menuList = menuService.list(menuWrapper);
        }

        return ResultUtils.success(menuList);
    }

    @GetMapping(value = "/getMenuListByUserId")
    public ResultBean getMenuListByUserId(@RequestParam("userId") String userId) {
        List<Menu> menuList = new ArrayList<>();

        // 1.根据perm表里面的entityId和resourceCode查询出所有菜单perm，取resourceInstanceId
        QueryWrapper<UserRole> userRoleWrapper = new QueryWrapper<>();
        userRoleWrapper.eq("user_id", userId);
        List<UserRole> userRoleList = userRoleService.list(userRoleWrapper);
        if (userRoleList != null && !userRoleList.isEmpty()) {
            QueryWrapper wrapper = new QueryWrapper<>();
            wrapper.in("entity_id", userRoleList.stream().map(UserRole::getRoleId).collect(Collectors.toList()));
            wrapper.eq("resource_code", ResourceTypeEnum.MENU.getCode());
            List<Perm> permList = permService.list(wrapper);
            if (permList != null && !permList.isEmpty()) {
                // 2.根据菜单resourceInstanceId到menu表中取出菜单数据【补数据，然后确认授权哪里是OK的】
                QueryWrapper menuWrapper = new QueryWrapper();
                menuWrapper.in("id", permList.stream().map(Perm::getResourceInstanceId).collect(Collectors.toList()));
                menuList = menuService.list(menuWrapper);
            }
        }

        return ResultUtils.success(menuList);
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
