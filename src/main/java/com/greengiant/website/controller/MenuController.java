package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController // todo 要统一一下，不要有的地方用，有的地方不用
@RequestMapping("/menu")
public class MenuController {

    // todo MenuController和PermissionController的整合

    @Autowired
    private MenuService menuServce;

    @PostMapping(value = "/selectByUserId")
    public ResultBean addMenu() {
        // todo
        return null;
    }

    @GetMapping(value = "/selectByUserId")
    public ResultBean selectByUserId(@RequestParam long userId){
        return ResultUtils.success(menuServce.selectByUserId(userId));
    }

}
