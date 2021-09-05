package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    // todo menu是资源，需要在资源的增删查改的地方联动Perm类

    @Autowired
    private MenuService menuService;

    @PostMapping(value = "/add")
    public ResultBean addMenu(@RequestBody Menu menu) {
        return ResultUtils.success(menuService.save(menu));
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
