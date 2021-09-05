package com.greengiant.website.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.greengiant.website.pojo.model.Perm;
import com.greengiant.website.pojo.model.Role;
import com.greengiant.website.pojo.model.RolePermission;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.service.PermService;
import com.greengiant.website.service.RolePermissionService;
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
import org.springframework.stereotype.Component;

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
    private RolePermissionService rolePermissionService;

    @Autowired
    private PermService permService;

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
//        authorizationInfo.addStringPermission();// todo 把添加权限放这里怎么样？是不是就可以用WildcardPermission了？

        // todo 更新登录IP和时间

        // 记录登录日志
        log.info(username + "系统登录");

        return authorizationInfo;
    }


    @Override
    public  boolean isPermitted(PrincipalCollection principals, String permission){
        if (permission == null || !permission.contains(":")) {// todo 后续可以做的更灵活，例如没有3段。并且把算法错误干掉
            log.error("权限字符串格式错误");
            return false;
        }
        String[] permStr = permission.split(":");
        if (permStr.length != 3) {
            log.error("权限字符串格式错误");
            return false;
        }

        String username = principals.toString();
        List<Role> roleList = null;
        roleList = (List<Role>)this.getCacheManager().getCache(cacheName).get(username);
        if (roleList == null) {
            // todo 更新角色的時候也要更新role信息
            roleList = roleService.getRoleListByUserName(username);
            this.getCacheManager().getCache(cacheName).put(username, roleList);
        }

        QueryWrapper<Perm> permWrapper = new QueryWrapper<>();
        permWrapper.apply("(resource={0} or resource={1})", permStr[0], "*");
        permWrapper.and(wp -> wp.like("operation", permStr[1]).or().eq("operation", "*"));
        permWrapper.and(wp -> wp.eq("resource_instance", permStr[2]).or().eq("resource_instance", "*"));
        Perm perm = permService.getOne(permWrapper);

        if (perm != null && roleList != null && !roleList.isEmpty()) {
            for (Role role : roleList) {
                // 获取rolePerm列表
                QueryWrapper<RolePermission> wrapper = new QueryWrapper<>();
                wrapper.eq("role_id", role.getId());
                wrapper.eq("permission_id", perm.getId());
                RolePermission rolePerm = rolePermissionService.getOne(wrapper);
                if (rolePerm != null) {
                    return true;
                }
            }
        }

        return false;
    }

}
