package com.greengiant.website.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminController {
//    @Autowired
//    private final ResultMap resultMap;

    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    public String getMessage() {
        //return resultMap.success().message("您拥有管理员权限，可以获得该接口的信息！");
        return "您拥有管理员权限，可以获得该接口的信息！";
    }
}
