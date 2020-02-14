package com.greengiant.website.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greengiant.website.pojo.model.Menu;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.service.MenuService;
import com.greengiant.website.service.RoleService;
import com.greengiant.website.service.UserService;
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
import java.util.List;
import java.util.Set;

@Slf4j
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        log.info("authenticate for:[{}]", authenticationToken.getPrincipal());

        // 这里是自定义登录验证用户名密码的规则
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取用户输入的用户名
        String username = (String) token.getPrincipal();
        // 从数据库获取对应用户名密码的用户
        User user = userService.getByName(token.getUsername());
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
        // todo 加缓存
        log.info("获取角色信息");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        log.info("authorization for: [{}]" + username);
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        Set<String> roleSet = new HashSet<>();
        //获得该用户角色
        List<Role> roleList = roleService.getRoleListByUserName(username);
        // todo 这里是否需要双重判断？
        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                roleSet.add(role.getRoleName());
            }
            //设置该用户拥有的角色
            info.setRoles(roleSet);
        }

        return info;
    }

    // todo 添加permission的用法是info.addStringPermission(perms)，能否用这个来修改我的授权操作？（比较一下用和不用的区别）
    //  https://www.cnblogs.com/116970u/p/10954812.html
    @Override
    public  boolean isPermitted(PrincipalCollection principals, String permission){
//        String username = (String) SecurityUtils.getSubject().getPrincipal();
        String username = principals.toString();
        QueryWrapper<Menu> menuWrapper = new QueryWrapper<>();
        menuWrapper.eq("menu_name", permission);
        Menu menu = menuService.getOne(menuWrapper);
        if (menu == null) {
            return false;
        }

        List<Role> roleList = roleService.getRoleListByUserName(username);
        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                if (menu.getRole() != null && menu.getRole().contains(role.getId().toString())) {
                    return true;
                }
            }
        }

        return false;
    }

//    @Override
//    public boolean hasRole(PrincipalCollection principals, String roleIdentifier) {
//        SecurityUser user = (SecurityUser)principals.getPrimaryPrincipal();
//        return user.isAdmin()||super.hasRole(principals,roleIdentifier);
//    }
}
