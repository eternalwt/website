package com.greengiant.website.config;

import com.greengiant.infrastructure.utils.PasswordUtil;
import com.greengiant.website.shiro.CustomCachedSessionDAO;
import com.greengiant.website.shiro.CustomRealm;
import com.greengiant.website.shiro.RetryLimitHashedCredentialsMatcher;
import com.greengiant.website.shiro.ShiroCacheManagerImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    /**
     * 设置过滤器，将自定义的Filter加入
     */
    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        shiroFilterFactoryBean.setLoginUrl("/notLogin");// todo 通过filter和这种方式都能够设置。看一下代码：https://blog.csdn.net/u012475575/article/details/82022745
        //  自定义logoutfilter来跳转到登录页：https://www.cnblogs.com/ningheshutong/p/8134008.html
        //todo 和后端action对应起来。success的url是什么时候用的？
        shiroFilterFactoryBean.setSuccessUrl("loginSuccess");// todo 要测试一下。登录成功默认跳转页面，不配置则跳转至”/”，可以不配置，直接通过代码进行处理。
        // 设置（已登录）无权限时跳转的 url
        // todo 下面的用法是不是错了，是不是应该结合后端方法（例如notLogin）来处理？
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");// todo 给权限不足的提示，并测试
        // 设置拦截器 TODO 上面的url设置和filter的关系？
        shiroFilterFactoryBean.setFilterChainDefinitionMap(this.getfilterChainDefinitionMap());
        log.info("Shiro拦截器工厂类注入成功");

        return shiroFilterFactoryBean;
    }

    private Map<String, String> getfilterChainDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // todo 运维，调试完后应该加上限制
        // todo swagger路径用2个*配置也不行，再思考一下
        // todo 这里能否拿到数据库管理？
        // todo 20210905：filterChainDefinitionMap是不是可以不要？
        // 默认过滤器见DefaultFilter todo 20211211 才发现这里对应的是接口权限（再看看Filter代码）。接口权限很难做好，先做需要鉴权和不需要鉴权，界面控制权限粒度
        filterChainDefinitionMap.put("/test/**", "anon");

        filterChainDefinitionMap.put("/websocket/**", "anon");
        filterChainDefinitionMap.put("/menu/**", "anon");
        filterChainDefinitionMap.put("/perm/**", "anon");
        filterChainDefinitionMap.put("/article/**", "anon");

        filterChainDefinitionMap.put("/swagger**", "anon");
        filterChainDefinitionMap.put("/actuator/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        filterChainDefinitionMap.put("/website-websocket/**", "anon");

        // 游客，开放权限
        filterChainDefinitionMap.put("/guest/**", "anon");
        // 开放登陆接口
        filterChainDefinitionMap.put("/auth/**", "anon");
        // 用户，需要角色权限 “user”
        filterChainDefinitionMap.put("/user/**", "anon");
        filterChainDefinitionMap.put("/role/**", "anon");
        // 管理员，需要角色权限 “admin”
        // todo RolesAuthorizationFilter怎么起作用的其实我并不知道。与CustomRealm的关系？
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        // todo AuthorizationFilter怎么用上？ PermissionsAuthorizationFilter
        //  todo 试一下自定义过滤器
//        filterChainDefinitionMap.put("/logout", "logout"); // todo logout过滤器有什么作用？jwt是不是可以用上？
        // 其余接口一律拦截（这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截）
        filterChainDefinitionMap.put("/**", "user");

        return filterChainDefinitionMap;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm,
                                           RetryLimitHashedCredentialsMatcher hashedCredentialsMatcher,
                                           ShiroCacheManagerImpl shiroCacheManager,
                                           CookieRememberMeManager rememberMeManager,
                                           DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 注入自定义的realm
        customRealm.setAuthenticationCacheName("authenticationCache");
        customRealm.setAuthorizationCacheName("authorizationCache");
        customRealm.setCachingEnabled(true);
        customRealm.setAuthenticationCachingEnabled(true);
        customRealm.setAuthorizationCachingEnabled(true);
        //hash算法
        hashedCredentialsMatcher.setHashAlgorithmName(PasswordUtil.ALGORITHM_NAME);
        //加密次数
        hashedCredentialsMatcher.setHashIterations(PasswordUtil.HASH_ITERATION_COUNT);
        //存储散列后的密码是否为16进制
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(PasswordUtil.STORED_CREDENTIALS_HEX_ENCODED);
        customRealm.setCredentialsMatcher(hashedCredentialsMatcher);

        securityManager.setRealms(Arrays.asList(customRealm));

        // 注入缓存管理器
        securityManager.setCacheManager(shiroCacheManager);
        // 注入记住密码管理器
        securityManager.setRememberMeManager(rememberMeManager);
        // 注入session管理器
        securityManager.setSessionManager(sessionManager);

        return securityManager;
    }

    /**
     * 自定义身份认证 realm
     *
     */
//    @Bean
//    public CustomRealm customRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
//        CustomRealm realm = new CustomRealm();
//
//        realm.setAuthenticationCacheName("authenticationCache");
//        realm.setAuthorizationCacheName("authorizationCache");
//        realm.setCachingEnabled(true);
//        realm.setAuthenticationCachingEnabled(true);
//        realm.setAuthorizationCachingEnabled(true);
//
//        //hash算法
//        hashedCredentialsMatcher.setHashAlgorithmName(PasswordUtil.ALGORITHM_NAME);
//        //加密次数
//        hashedCredentialsMatcher.setHashIterations(PasswordUtil.HASH_ITERATION_COUNT);
//        //存储散列后的密码是否为16进制
//        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(PasswordUtil.STORED_CREDENTIALS_HEX_ENCODED);
//        realm.setCredentialsMatcher(hashedCredentialsMatcher);
//
//        return realm;
//    }

    /**
     * Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        // cookie的名称
        SimpleCookie simpleCookie = new SimpleCookie("REMEMBERCOOKIE");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        // 有效时间1个月,单位是秒。如果要按没有操作时间来算，需要每次发送Set-Cookie，影响性能不划算  // todo 下面有个地方是毫秒，这里再测试一下
        simpleCookie.setMaxAge(60 * 60 * 24 * 30);

        return simpleCookie;
    }

    /**
     * cookie管理器
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        // cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        // todo 密钥太短，根据上面的注释生成长的
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie);

        return cookieRememberMeManager;
    }

    @Bean
    public DefaultWebSessionManager sessionManager(CustomCachedSessionDAO customCachedSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //单位毫秒，1小时后失效
        sessionManager.setGlobalSessionTimeout(1000 * 60 * 60);
        // 相隔多久检查一次session的有效性
        sessionManager.setSessionValidationInterval(1000 * 60 * 15);
        // 删除失效session
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionDAO(customCachedSessionDAO);// todo【这句话和下面一句话冲突】 可以不要，CustomCachedSessionDAO实现了CacheManagerAware接口
        // todo 默认是MemorySessionDAO 只要自己结合redis实现了SessionDAO，就具有了服务扩展能力，就可以不用管Spring Session（能否在SessionDAO里面使用Spring Session？）

        return sessionManager;
    }

    /**
     * Shiro生命周期处理器
     * @return
     */
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
     * @return
     */
    @Bean
    @DependsOn({"lifecycleBeanPostProcessor"})
    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
        // 强制使用cglib
        advisorAutoProxyCreator.setProxyTargetClass(true);
        return advisorAutoProxyCreator;
    }

    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
        return authorizationAttributeSourceAdvisor;
    }

}
