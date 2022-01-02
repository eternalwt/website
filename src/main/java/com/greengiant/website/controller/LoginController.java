package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.CaptchaUtil;
import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.service.AuthService;
import com.greengiant.website.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthService loginService;

    @Autowired
    private UserService userService;

    @Autowired
    private CacheManager cacheManager;

    @GetMapping(value = "/notLogin")
    public String notLogin() {
        //todo 前端给几秒提示，然后转到登录页面
        return "您尚未登陆！";
    }

    @GetMapping(value = "/notRole")
    public String notRole() {
        return "您没有权限！";
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping(value = "/login")
    public ResultBean login(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam(required = false, defaultValue = "false") boolean isRememberMe) {
        // 从SecurityUtils里边创建一个 subject
        loginService.login(username, password, isRememberMe);
        System.out.println("isRunAs:" + SecurityUtils.getSubject().isRunAs());
        return ResultUtils.success("login success");
    }

    @PostMapping(value = "/runAs")// todo 如何保证不通过网址fake？
    public ResultBean runAs(String userName) {//todo 是否使用userId
//        PrincipalCollection pc
//        SecurityUtils.getSubject().runAs();
        // todo
        return null;
    }

    /**
     * 注销
     * @return ResultBean
     */
    @GetMapping(value = "/logout")
    public ResultBean logout() {

        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return ResultUtils.success("登出成功！");
    }

    @RequestMapping("/captchaCode")// todo 再搞一遍
    public void getCode(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        CaptchaUtil.getCode(req, resp);
    }


}
