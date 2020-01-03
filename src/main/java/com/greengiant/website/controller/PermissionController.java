package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {

//    @RequiresRoles("admin")
//    @RequiresPermissions("aaa")
    @RequestMapping(value = "/isPermitted", method = RequestMethod.GET)
    public ResultBean checkPermission() {
        Subject subject = SecurityUtils.getSubject();
        subject.hasRole("admin");
        boolean result = subject.isPermitted("admin");
        // todo 这个方法测完可以干掉。这里为啥principal为空？是不是没传session等过来？
        return ResultUtils.success();
    }
}
