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
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Component
public class CustomRealm extends AuthorizingRealm {
    //todo

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

        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        token.setRememberMe(token.isRememberMe());
        // 从数据库获取对应用户名密码的用户
        User user = userDao.selectByName(token.getUsername());
        if (null == user) {
            throw new AccountException("用户名不正确");
        }
        // todo 加一个判断账号是否被禁 isLocked
        String password = user.getPassword();
        String salt = user.getPasswordSalt();

        return new SimpleAuthenticationInfo(token.getPrincipal(), password, ByteSource.Util.bytes(salt),
                this.getName());
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
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        log.info("authorization for: [{}]" + username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        //需要将 role 封装到 Set 作为 info.setRoles() 的参数
        Set<String> set = new HashSet<>();
        //获得该用户角色
        Role role = roleDao.selectByName(username);
        if (role != null) {
            set.add(role.getRoleName());
            //设置该用户拥有的角色
            info.setRoles(set);
        }

        return info;
    }
}
