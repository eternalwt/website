package com.greengiant.website.service.impl;

import com.greengiant.website.service.AuthService;
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
        subject.login(token);// todo 在这个地方统一一下，就可以用jwtToken了
    }

}
