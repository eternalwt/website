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
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
public class CustomRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private MenuService menuService;

    private static final String cacheName = "roleCache";

    /**
     * 获取身份验证信息
     * Shiro中，最终是通过 Realm 来获取应用程序中的用户、角色及权限信息的。
     * @param authenticationToken 用户身份信息 token
     * @return 返回封装了用户信息的 AuthenticationInfo 实例
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        // todo 这里日志参数好像打的不对
        log.info("authenticate for:[{}]", authenticationToken.getPrincipal());

        // 这里是自定义登录验证用户名密码的规则
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        // 获取用户输入的用户名
        String username = (String) token.getPrincipal();
        // 从数据库获取对应用户名密码的用户
        User user = userService.getByName(token.getUsername());
        if (null == user) {
            throw new UnknownAccountException("用户名错误");
            // 其他错误在matcher里面处理
        }
        String salt = user.getPasswordSalt();

        // todo 真正的校验过程还是要看一下
        return new SimpleAuthenticationInfo(username, user.getPassword(), ByteSource.Util.bytes(salt), this.getName());
    }

    /**
     * 获取授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();

        log.info("获取角色信息");
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        // todo 其实这里可以直接在方法上加注解做缓存，这样是不是好的实践值得商榷一下
        log.info("authorization for: [{}]" + username);
        //获得该用户角色
        List<Role> roleList = null;
        if (this.getCacheManager().getCache(cacheName) != null) {
            roleList = (List<Role>)this.getCacheManager().getCache(cacheName).get(username);
        }
        if (roleList == null) {
            roleList = roleService.getRoleListByUserName(username);
            this.getCacheManager().getCache(cacheName).put(username, roleList);
        }

        if (roleList != null && !roleList.isEmpty()) {
            Set<String> roleSet = new HashSet<>();
            for (Role role : roleList) {
                roleSet.add(role.getRoleName());
            }
            authorizationInfo.setRoles(roleSet);
        }

        return authorizationInfo;
    }


    @Override
    public  boolean isPermitted(PrincipalCollection principals, String permission){
        String username = principals.toString();
        QueryWrapper<Menu> menuWrapper = new QueryWrapper<>();
        menuWrapper.eq("menu_name", permission);
        Menu menu = menuService.getOne(menuWrapper);
        if (menu == null) {
            return false;
        }

        List<Role> roleList = null;
        roleList = (List<Role>)this.getCacheManager().getCache(cacheName).get(username);
        if (roleList == null) {
            // todo 更新角色的時候也要更新role信息
            roleList = roleService.getRoleListByUserName(username);
            this.getCacheManager().getCache(cacheName).put(username, roleList);
        }

        if (roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                if (menu.getRole() != null && menu.getRole().contains(role.getId().toString())) {
                    return true;
                }
            }
        }

        return false;
    }

}
