package com.greengiant.website.controller;

import com.greengiant.website.service.LoginService;
import com.greengiant.website.service.RoleService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private RoleService roleService;

    @RequestMapping(value = "/notLogin", method = RequestMethod.GET)
    public String notLogin() {
        //todo 前端给几秒提示，然后转到登录页面
        return "您尚未登陆！";
    }

    @RequestMapping(value = "/notRole", method = RequestMethod.GET)
    public String notRole() {
        return "您没有权限！";
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     */
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@RequestParam String username,
                        @RequestParam String password) {
        // 从SecurityUtils里边创建一个 subject
        loginService.login(username, password);
        //根据权限，指定返回数据
        return roleService.getRole(username);
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return "成功注销！";
    }



}
