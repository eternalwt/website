package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/perm")
public class PermController {

    @GetMapping(value = "/check")
    public ResultBean check(@RequestParam("perm") String perm) {
        Subject subject = SecurityUtils.getSubject();
        subject.checkPermission("admin::aaa");

        // todo
        return ResultUtils.success("登出成功！");
    }

}
