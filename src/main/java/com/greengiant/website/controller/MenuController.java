package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/menu")
public class MenuController {

    // todo MenuController和PermissionController的整合

    @Autowired
    private MenuService menuServce;

    @PostMapping(value = "/add")
    public ResultBean addMenu(@RequestBody Menu menu) {
        return ResultUtils.success(menuServce.save(menu));
    }

    @GetMapping(value = "/selectByUserId")
    public ResultBean selectByUserId(@RequestParam long userId){
        return ResultUtils.success(menuServce.selectByUserId(userId));
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
        return ResultUtils.success(menuServce.getMenuTree());
    }

}
