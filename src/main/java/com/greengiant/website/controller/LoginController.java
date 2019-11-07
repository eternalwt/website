package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.service.AuthService;
import com.greengiant.website.service.RoleService;
import com.greengiant.website.service.UserService;
import com.greengiant.website.utils.JWTUtil;
import com.greengiant.website.utils.ResultUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/auth")
public class LoginController {

    @Autowired
    private AuthService loginService;

    @Autowired
    private UserService userService;

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
    @PostMapping(value = "/login")
    public ResultBean login(@RequestParam String username,
                            @RequestParam String password,
                            @RequestParam(required = false, defaultValue = "false") boolean isRememberMe) {
        // todo 判空与异常处理

        // 从SecurityUtils里边创建一个 subject
        loginService.login(username, password, isRememberMe);
        //根据权限，指定返回数据
        User user = userService.getByName(username);

        return ResultUtils.success(user.getId());
    }

    /**
     * 登陆
     *
     * @param username 用户名
     * @param password 密码
     */
    @PostMapping(value = "/jwtlogin")
    public ResultBean jwtLogin(@RequestParam String username,
                        @RequestParam String password,
                        @RequestParam(required = false, defaultValue = "false") boolean isRememberMe,
                        HttpServletResponse response) {
        // todo jwt怎么做rememberMe功能？
        // 从SecurityUtils里边创建一个 subject
        // todo 要把密码封进去
        String token = JWTUtil.createToken(username, password);
        loginService.jwtLogin(token);
        response.setHeader("x-auth-token", token);
        //根据权限，指定返回数据
        return ResultUtils.success(roleService.getRole(username));
    }

    /**
     * 注销
     * @return
     */
    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public ResultBean logout() {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();

        return ResultUtils.success("登出成功！");
    }



}
