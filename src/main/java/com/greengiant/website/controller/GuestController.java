package com.greengiant.website.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/guest")
public class GuestController{
//    @Autowired
//    private final ResultMap resultMap;

    @RequestMapping(value = "/enter", method = RequestMethod.GET)
    public String login() {
        //return resultMap.success().message("欢迎进入，您的身份是游客");
        return "欢迎进入，您的身份是游客";
    }

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public String submitLogin() {
        //return resultMap.success().message("您拥有获得该接口的信息的权限！");
        return "您拥有获得该接口的信息的权限！";
    }
}
