package com.greengiant.website.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController{
//    @Autowired
//    private final ResultMap resultMap;

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public String getMessage() {
        //return resultMap.success().message("您拥有用户权限，可以获得该接口的信息！");
        return "您拥有用户权限，可以获得该接口的信息！";
    }
}

