package com.greengiant.website.controller;

import com.greengiant.website.pojo.vo.AddUserVo;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
public class UserController{

    // admin路径

    //todo addUser editUser delUser listUser

    @PostMapping(value = "/add")
    public String addUser(@RequestBody AddUserVo userVo) {
        //todo
        //1.判断是否有同名的已存在，如果有给出fail的返回值
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

