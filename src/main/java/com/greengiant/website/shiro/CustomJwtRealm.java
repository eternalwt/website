package com.greengiant.website.shiro;

import com.greengiant.website.dao.UserDao;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.utils.JWTUtil;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class CustomJwtRealm extends AuthorizingRealm {

    @Autowired
    private UserDao userDao;

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
        if (username == null || !JWTUtil.verify(token, username)) {
            throw new AuthenticationException("token认证失败！");
        }

        User user = userDao.selectByName(username);

////        if (!JWTUtil.verify(username, user.getPassword(), token)) {
//        if (!JWTUtil.verify(token, username)) {
//            throw new UnknownAccountException("Username or password error");
//        }

        return new SimpleAuthenticationInfo(token, token, getName());
    }

    /**
     * 只有当需要检测用户权限的时候才会调用此方法，例如checkRole,checkPermission之类的
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        System.out.println("————权限认证————");
//        String username = JWTUtil.getUsername(principals.toString());
//        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        //获得该用户角色
//        String role = userMapper.getRoleId(username);
//        //每个角色拥有默认的权限
//        String rolePermission = userMapper.getRolePermission(username);
//        //每个用户可以设置新的权限
//        String permission = userMapper.getPermission(username);
//        Set<String> roleSet = new HashSet<>();
//        Set<String> permissionSet = new HashSet<>();
//        //需要将 role, permission 封装到 Set 作为 info.setRoles(), info.setStringPermissions() 的参数
//        roleSet.add(role);
//        permissionSet.add(rolePermission);
//        permissionSet.add(permission);
//        //设置该用户拥有的角色和权限
//        info.setRoles(roleSet);
//        info.setStringPermissions(permissionSet);
//        return info;

        return null;
    }
}
