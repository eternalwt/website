package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.service.AuthService;
import com.greengiant.website.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.web.bind.annotation.*;

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
        // todo 判断是否有密码输入次数锁定、账号锁定
        // 从SecurityUtils里边创建一个 subject
        loginService.login(username, password, isRememberMe);
        //根据权限，指定返回数据
        User user = userService.getByName(username);

        return ResultUtils.success(user);
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


}
