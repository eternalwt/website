package com.greengiant.website.shiro;

import com.greengiant.website.dao.RoleMapper;
import com.greengiant.website.dao.UserMapper;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.utils.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashSet;
import java.util.Set;

public class CustomJwtRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private RoleMapper roleMapper;

    /**
     * 必须重写此方法，不然会报错
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return null != token && token instanceof JWTToken;
    }

    /**
     * 默认使用此方法进行用户名正确与否验证，错误抛出异常即可。
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        String token = (String) authenticationToken.getCredentials();
        // 解密获得username，用于和数据库进行对比
        String username = JWTUtil.getUsername(token);
        User user = userMapper.selectByName(username);
        if (username == null || user == null || !JWTUtil.verify(token, username, user.getPassword(), user.getPasswordSalt())) {
            throw new AuthenticationException("token认证失败！");
            //throw new UnknownAccountException("Username or password error");
        }

        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String token = principals.toString();
        //根据token获取权限授权
        String username = JWTUtil.getUsername(token);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> set = new HashSet<>();
        //获得该用户角色
        Role role = roleMapper.selectByName(username);
        if (role != null) {
            set.add(role.getRoleName());
            //设置该用户拥有的角色
            info.setRoles(set);
        }

        return info;
    }
}
