package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.StatusCodeEnum;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.pojo.vo.AddUserVo;
import com.greengiant.website.service.UserService;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class UserController{

    // admin路径

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
        //todo
        //2.分别在user和role关联表里面写数据

        //3.注意：加密和事务。看些这些东西能否用上：PlatformTransactionManager、DefaultTransactionDefinition、TransactionStatus

        //看看异常在哪一层怎么处理，manager？事务放在哪一层的问题

        return null;
    }

  @GetMapping(value = "/getMessage")
    public String getMessage() {
        return "您拥有用户权限，可以获得该接口的信息！";
    }
}

