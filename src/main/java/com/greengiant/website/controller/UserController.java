package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.PageParam;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.StatusCodeEnum;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.query.PageQuery;
import com.greengiant.website.pojo.query.UserQuery;
import com.greengiant.website.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;

    @PostMapping(value = "/add")
    public ResultBean addUser(@RequestBody UserQuery userQuery) {
        if (userQuery.getUserName() == null || userQuery.getPassword() == null) {
            return ResultUtils.paramError();
        }

        //1.判断是否有同名的已存在，如果有给出fail的返回值
        User user = userService.getByName(userQuery.getUserName());
        if (user != null) {
            return ResultUtils.fail(StatusCodeEnum.USER_EXISTS.getCode(), StatusCodeEnum.USER_EXISTS.getMsg());
        }
        //2.分别在user和role关联表里面写数据
        return ResultUtils.success(userService.addUser(userQuery));
    }

    @PostMapping(value = "/edit")
    public ResultBean editUser(@RequestBody User user) {
        if (user.getId() == null) {
            return ResultUtils.paramError();
        }

        return ResultUtils.success(userService.updateById(user));
    }

    @PostMapping(value = "/password/change")
    public ResultBean changePassword(@RequestBody UserQuery userQuery) {
        if (userQuery.getUserName() == null || userQuery.getPassword() == null) {
            return ResultUtils.paramError();
        }

        //1.判断用户名是否存在
        User user = userService.getByName(userQuery.getUserName());
        if (user == null) {
            return ResultUtils.fail();
        }

        return ResultUtils.success(userService.changePassword(user, userQuery.getPassword()));
    }

    @PostMapping("/list")
    public ResultBean getPageList(@RequestBody PageQuery<User> pageQuery) {
        IPage<User> result = userService.getPageList(pageQuery);

        return ResultUtils.success(result);
    }

    @PostMapping(value = "/delete")
    public ResultBean delUser(@RequestParam Long userId) {
        return ResultUtils.success(userService.delUser(userId));
    }

}

