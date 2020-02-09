package com.greengiant.website.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.greengiant.website.pojo.PageParam;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.StatusCodeEnum;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.vo.AddUserQuery;
import com.greengiant.website.service.UserService;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;

    //todo editUser delUser listUser

    @PostMapping(value = "/add")
    public ResultBean addUser(@RequestBody AddUserQuery userVo) {
        //1.判断是否有同名的已存在，如果有给出fail的返回值
        // todo 判空
        User user = userService.getByName(userVo.getUserName());
        if (user != null) {
            return ResultUtils.fail(StatusCodeEnum.USER_EXISTS.getCode(), StatusCodeEnum.USER_EXISTS.getMsg());
        }
        //2.分别在user和role关联表里面写数据
        userService.addUser(userVo);

        // todo 注册后如何自动登录？

        //todo 看看异常在哪一层怎么处理，manager？事务放在哪一层的问题

        return ResultUtils.success();
    }

    @PostMapping(value = "/password/change")
    public ResultBean changePassword(@RequestBody AddUserQuery userVo) {
        // todo 写好
        //1.判断是否有同名的已存在，如果有给出fail的返回值
        // todo 判空
        User user = userService.getByName(userVo.getUserName());
        if (user != null) {
            return ResultUtils.fail(StatusCodeEnum.USER_EXISTS.getCode(), StatusCodeEnum.USER_EXISTS.getMsg());
        }
        //2.分别在user和role关联表里面写数据
        userService.addUser(userVo);

        //todo 看看异常在哪一层怎么处理，manager？事务放在哪一层的问题
        //todo 修改密码后，对登录/JWT的影响？

        return ResultUtils.success();
    }

    @RequestMapping("/users")
    public ResultBean getUserList() {
        return ResultUtils.success(userService.list());
    }

    @RequestMapping("/getUserListByPage")
    public ResultBean getUserListByPage(@RequestBody PageParam pageParam) {
        IPage<User> page = new Page<>(pageParam.getPageNum(), pageParam.getPageSize());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        // todo 分页加条件过滤，形成一个通用的操作
        IPage<User> result = userService.page(page, wrapper);

        return ResultUtils.success(result);
    }

}

