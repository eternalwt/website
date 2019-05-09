package com.greengiant.website.controller;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

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
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        subject.login(token);
//        //根据权限，指定返回数据
//        String role = userMapper.getRole(username);
//        if ("user".equals(role)) {
//            return "欢迎登陆";
//        }
//        if ("admin".equals(role)) {
//            return "欢迎来到管理员页面";
//        }

        return "权限错误！";
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
