package com.greengiant.website.shiro;

import com.greengiant.website.dao.RoleDao;
import com.greengiant.website.dao.UserDao;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.pojo.model.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

@Slf4j
//@Component
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserDao userDao;

    @Autowired
    private RoleDao roleDao;

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        log.info("authenticate for:[{}]", authenticationToken.getCredentials());

        // 这里是自定义登录验证用户名密码的规则
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取用户输入的用户名
        String username = (String) token.getPrincipal();
        // 从数据库获取对应用户名密码的用户
        User user = userDao.selectByName(token.getUsername());
        if (null == user) {
            throw new AccountException("用户名不正确");
        }

        String password = user.getPassword();// todo 为啥用的是数据库里面取出来的password？
        String salt = user.getPasswordSalt();

        return new SimpleAuthenticationInfo(username, password, ByteSource.Util.bytes(salt), this.getName());
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // todo 这里应该是没写好的。应该可以用带role的filter来测试把？跟动态配置权限有没有关系
        // todo 如何提示用户权限不足？
        // 这里是自定义如何返回角色信息，默认的是啥？
        log.info("进入角色授权");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        log.info("authorization for: [{}]" + username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        //获得该用户角色
        Role role = roleDao.selectByName(username);
        if (role != null) {
            set.add(role.getRoleName());
            //设置该用户拥有的角色
            info.setRoles(set);
        }

        // todo info.addStringPermission(perms)的用法：https://www.cnblogs.com/116970u/p/10954812.html

        return info;
    }
}
