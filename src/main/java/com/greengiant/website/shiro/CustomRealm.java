package com.greengiant.website.shiro;

import com.greengiant.website.dao.RoleMapper;
import com.greengiant.website.dao.UserMapper;
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
public class CustomRealm extends AuthorizingRealm {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

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
        User user = userMapper.selectByName(token.getUsername());
        if (null == user) {
            throw new AccountException("用户名不正确");
        }

        String password = user.getPassword();
        String salt = user.getPasswordSalt();

        // todo 用的是数据库里面取出来的password，应该是跟UsernamePasswordToken里面封装的password比较，继续跟代码
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
        // todo 1.默认的是啥？跟踪默认代码
        // todo 2.我的参数principalCollection都没用上，debug一下
        // todo 3.带role的filter来测试（当初这句话啥意思？）
        log.info("进入角色授权");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        log.info("authorization for: [{}]" + username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        //获得该用户角色
        Role role = roleMapper.selectByName(username);
        if (role != null) {
            set.add(role.getRoleName());
            //设置该用户拥有的角色
            info.setRoles(set);
            // 添加permission的用法是info.addStringPermission(perms)的用法：https://www.cnblogs.com/116970u/p/10954812.html
        }

        return info;
    }
}
