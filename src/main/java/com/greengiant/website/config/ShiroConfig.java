package com.greengiant.website.config;

import com.greengiant.website.shiro.CustomRealm;
import com.greengiant.website.shiro.RetryLimitHashedCredentialsMatcher;
import com.greengiant.website.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {// todo securityManager是怎么传入的？
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射【看一下代码】
        //todo 和后端action对应起来。success的url是什么时候用的？
        shiroFilterFactoryBean.setLoginUrl("/notLogin.html");
        //todo 测试，应该是登录成功后跳转的页面吧
        shiroFilterFactoryBean.setSuccessUrl("loginSuccess");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        // 设置拦截器
        // todo 把下面这一块抽一下，更有利于JWT的配置
        // todo 从数据库读取目录和权限对应关系【需要结合自己有多少个filter】。那么问题就变成：如何把功能和url对应起来（一个完善的路由机制）
        // todo 用一个表保存role和有权限的页面之间的对应关系，既用于加载页面，又用于鉴权。但是这样好像没用到permission表？再考虑一下
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //todo 运维，调试完后应该加上限制
        //todo swagger路径用2个*配置也不行，再思考一下
        filterChainDefinitionMap.put("/swagger**", "anon");
        filterChainDefinitionMap.put("/actuator/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        //游客，开发权限
        filterChainDefinitionMap.put("/guest/**", "anon");
        //开放登陆接口
        filterChainDefinitionMap.put("/auth/**", "anon");
        //用户，需要角色权限 “user”
        filterChainDefinitionMap.put("/user/**", "user");
        //filterChainDefinitionMap.put("/user/**", "roles[user]");
        //管理员，需要角色权限 “admin”
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        //其余接口一律拦截
        //主要这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截
        filterChainDefinitionMap.put("/**", "user");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("Shiro拦截器工厂类注入成功");

        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm, CacheManager ehCacheCacheManager) {
        // todo CacheManager、RememberMeManager、sessionManager
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入缓存管理器
        securityManager.setCacheManager(ehCacheCacheManager);
        // todo 把RememberMe的cookie改名
        //securityManager.setRememberMeManager();

        // 注入自定义的realm
        securityManager.setRealm(customRealm);

        return securityManager;
    }

    /**
     * 自定义身份认证 realm
     *
     */
    @Bean
    public CustomRealm customRealm(HashedCredentialsMatcher hashedCredentialsMatcher) {
        CustomRealm realm = new CustomRealm();
        realm.setAuthenticationCacheName("authenticationCache");
        realm.setAuthorizationCacheName("authorizationCache");
        realm.setCachingEnabled(true);
        realm.setAuthenticationCachingEnabled(true);
        realm.setAuthorizationCachingEnabled(true);
        realm.setCredentialsMatcher(hashedCredentialsMatcher);

        return realm;
    }

    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher() {
        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();

        //hash算法
        retryLimitHashedCredentialsMatcher.setHashAlgorithmName(PasswordUtil.algorithmName);
        //加密次数
        retryLimitHashedCredentialsMatcher.setHashIterations(PasswordUtil.hashIterationCount);
        //存储散列后的密码是否为16进制
        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(PasswordUtil.storedCredentialsHexEncoded);

        return retryLimitHashedCredentialsMatcher;
    }

    /**
     * shiro缓存管理器
     *
     */
    @Bean(name = "shiroEhCacheCacheManager")
    public CacheManager getEhCacheManager() {
        CacheManager cacheManager = new EhCacheManager();

        return cacheManager;
    }

//    @Bean
//    public PasswordService getPasswordService() {
//        //todo 写通后考虑，是否放入util里面去
//        DefaultPasswordService defaultPasswordService = new DefaultPasswordService();
//        DefaultHashService defaultHashService = new DefaultHashService();
//        defaultHashService.setHashAlgorithmName(PasswordUtil.algorithmName);
//        defaultHashService.setHashIterations(PasswordUtil.hashIterationCount);
//        defaultPasswordService.setHashService(defaultHashService);
//
//        return defaultPasswordService;
//    }

    /**
     * Shiro生命周期处理器
     * @return
     */
//    @Bean
//    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
//        return new LifecycleBeanPostProcessor();
//    }

//    /**
//     * 开启Shiro的注解(如@RequiresRoles,@RequiresPermissions),需借助SpringAOP扫描使用Shiro注解的类,并在必要时进行安全逻辑验证
//     * 配置以下两个bean(DefaultAdvisorAutoProxyCreator(可选)和AuthorizationAttributeSourceAdvisor)即可实现此功能
//     * @return
//     */
//    @Bean
//    @DependsOn({"lifecycleBeanPostProcessor"})
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator(){
//        DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator = new DefaultAdvisorAutoProxyCreator();
//        // 强制使用cglib
//        advisorAutoProxyCreator.setProxyTargetClass(true);
//        return advisorAutoProxyCreator;
//    }

//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(){
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager());
//        return authorizationAttributeSourceAdvisor;
//    }

}
