package com.greengiant.website.service;

import com.greengiant.website.dao.UserRoleDao;
import com.greengiant.website.model.UserRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginServiceImpl implements LoginService {
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public void login(String username, String password) {
// 从SecurityUtils里边创建一个 subject
        Subject subject = SecurityUtils.getSubject();
        // 在认证提交前准备 token（令牌）
        UsernamePasswordToken token = new UsernamePasswordToken(username, password);
        // 执行认证登陆
        subject.login(token);
    }

    @Override
    public String getRole(String username) {
        //根据权限，指定返回数据
        UserRole role = userRoleDao.getUserRoleByName(username);
        if (role != null) {
            //todo 重构
            if ("user".equals(role.getRolename())) {
                return "欢迎登陆";
            }
            if ("admin".equals(role.getRolename())) {
                return "欢迎来到管理员页面";
            }
        }
        return "获取角色信息失败";
    }
}
