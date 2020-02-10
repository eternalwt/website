package com.greengiant.website.service.impl;

import com.greengiant.website.service.AuthService;
import com.greengiant.website.shiro.JWTToken;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Override
    public void login(String username, String password, boolean isRememberMe) {
        // todo 加一个判断账号是否被禁 isLocked。判断是否被禁用、用户名是否存在，放在这里比较好把？
        // 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）【搜集认证信息】
        UsernamePasswordToken token = new UsernamePasswordToken(username, password, isRememberMe);
        // 执行认证登陆
        subject.login(token);

        // todo 下面这几行是为了测试 doGetAuthorizationInfo 方法，测完后换个地方
        subject.hasRole("admin");
        boolean result = subject.isPermitted("admin");
    }

    @Override
    public void jwtLogin(String token) {
        Subject subject = SecurityUtils.getSubject();
        JWTToken jwtToken = new JWTToken(token);
        subject.login(jwtToken);
    }

}
