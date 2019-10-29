package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.StatusCodeEnum;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.vo.AddUserVo;
import com.greengiant.website.service.UserService;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/user")
public class UserController{

    @Autowired
    private UserService userService;

    //todo addUser editUser delUser listUser

    @PostMapping(value = "/add")
    public ResultBean addUser(@RequestBody AddUserVo userVo) {
        //1.判断是否有同名的已存在，如果有给出fail的返回值
        // todo 判空
        User user = userService.getByName(userVo.getUserName());
        if (user != null) {
            return ResultUtils.fail(StatusCodeEnum.USER_EXISTS.getCode(), StatusCodeEnum.USER_EXISTS.getMsg());
        }
        //2.分别在user和role关联表里面写数据
        userService.addUser(userVo);

        //todo 看看异常在哪一层怎么处理，manager？事务放在哪一层的问题

        return ResultUtils.success();
    }

    @PostMapping(value = "/password/change")
    public ResultBean changePassword(@RequestBody AddUserVo userVo) {
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

    // todo 分页
    @RequestMapping("/users")
    public String getUserList() {
//        //todo
//        List<User> list = userDao.selectAll();
//        //todo 修改判断
//        if (list != null && list.size() > 0) {
//            return String.valueOf(list.size());
//        }
        return "empty list";
    }

}

