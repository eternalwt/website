package com.greengiant.website.manager;

import com.greengiant.website.dao.ShiroUserDao;
import com.greengiant.website.dao.UserRoleDao;
import com.greengiant.website.model.ShiroUser;
import com.greengiant.website.model.UserRole;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CustomRealm extends AuthorizingRealm {
    //todo 如何单元测试？

    private ShiroUserDao shiroUserDao;

    private UserRoleDao userRoleDao;

    @Autowired
    private void setShiroUserDao(ShiroUserDao shiroUserDao) {
        this.shiroUserDao = shiroUserDao;
    }

    @Autowired
    private void setUserRoleDao(UserRoleDao userRoleDao) {
        this.userRoleDao = userRoleDao;
    }

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //todo 改成logger
        System.out.println("————身份认证方法————");
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 从数据库获取对应用户名密码的用户
        ShiroUser user = shiroUserDao.getUserByName(token.getUsername());
        String password = "";
        if (null == user) {
            throw new AccountException("用户名不正确");
        } else {
            password = user.getPassword();
            if (!password.equals(new String((char[]) token.getCredentials()))) {
                throw new AccountException("密码不正确");
            }
        }
        return new SimpleAuthenticationInfo(token.getPrincipal(), password, this.getName());
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //todo 改成logger
        System.out.println("————权限认证————");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        Set<String> set = new HashSet<>();
        //获得该用户角色
        UserRole userRole = userRoleDao.getUserRoleByName(username);
        if (userRole != null) {
            set.add(userRole.getRolename());
            //设置该用户拥有的角色
            info.setRoles(set);
        }

        return info;
    }
}
