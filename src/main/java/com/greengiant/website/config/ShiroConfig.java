package com.greengiant.website.config;

import com.greengiant.website.shiro.CustomRealm;
import com.greengiant.website.shiro.RetryLimitHashedCredentialsMatcher;
import com.greengiant.website.utils.PasswordUtil;
import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.DefaultPasswordService;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authc.credential.PasswordService;
//import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    @Autowired
    private CustomRealm customRealm;

    @Autowired
    private EhCacheCacheManager cacheManager;

    @Autowired
    private RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher;

    //@Autowired
    //private SecurityManager securityManager;//todo 这是加过滤的时候加的，还是没明白用法

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {//shirFilter
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射【看一下代码】
        //todo 和后端action对应起来。success的url是什么时候用的？
        shiroFilterFactoryBean.setLoginUrl("/notLogin");//todo 配好静态页面
        shiroFilterFactoryBean.setSuccessUrl("loginSuccess");//todo 测试，应该是登录成功后跳转的页面吧
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        // 设置拦截器
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
        filterChainDefinitionMap.put("/**", "authc");//todo 这里是不是应该用user？用于一般网页、特殊网页的鉴权

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap);
        log.info("Shiro拦截器工厂类注入成功");

        return shiroFilterFactoryBean;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager() {
        // todo 配置Realm、CacheManager、RememberMeManager、sessionManager
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // todo
        // 注入缓存管理器;
        // securityManager.setCacheManager(ehCacheManager());//todo 究竟写在这一行还是下面一行？
        // 注入自定义的realm

        retryLimitHashedCredentialsMatcher.setHashAlgorithmName("md5");
        //加密次数
        retryLimitHashedCredentialsMatcher.setHashIterations(2);
        //存储散列后的密码是否为16进制
        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(PasswordUtil.storedCredentialsHexEncoded);
        retryLimitHashedCredentialsMatcher.cacheManager = cacheManager;

        customRealm.setCredentialsMatcher(retryLimitHashedCredentialsMatcher);

        securityManager.setRealm(customRealm);//customRealm() customRealmWithMatcher(getEhCacheManager())
        //securityManager.setSessionManager();
        // todo
        //securityManager.setRememberMeManager();

        return securityManager;
    }

//    /**
//     * 自定义身份认证 realm;
//     * <p>
//     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
//     * 否则会影响 CustomRealm类 中其他类的依赖注入
//     */
//    @Bean
//    public CustomRealm customRealm() {
//        CustomRealm realm = new CustomRealm();
//        //realm.setCacheManager();
//        //realm.setCachingEnabled();
//        // todo 改成@autowired
//        realm.setCredentialsMatcher(getHashedCredentialsMatcher(cacheManager));
//
//        return realm;
//    }

//    @Bean
//    public PasswordService getPasswordService() {
//        //todo 写通后考虑，是否放入util里面去
//        DefaultPasswordService defaultPasswordService = new DefaultPasswordService();
//        DefaultHashService defaultHashService = new DefaultHashService();
//        defaultHashService.setHashAlgorithmName(PasswordUtil.algorithmName);
//        defaultHashService.setHashIterations(PasswordUtil.hashIterationCount);
//        defaultPasswordService.setHashService(defaultHashService);
//
//        //todo
//        return defaultPasswordService;
//    }

    //    @Bean(name="customRealmWithMatcher")
//    public CustomRealm customRealmWithMatcher(CacheManager cacheManager) {
//        CustomRealm realm = new CustomRealm();
//        realm.setCacheManager(cacheManager);
//        //todo 代码跟踪
//        realm.setCachingEnabled(true);
//        RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
//        matcher.setHashAlgorithmName(PasswordUtil.algorithmName);
//        matcher.setHashIterations(PasswordUtil.hashIterationCount);
//        matcher.setStoredCredentialsHexEncoded(PasswordUtil.storedCredentialsHexEncoded);
//
////        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
////        matcher.setHashAlgorithmName(PasswordUtil.algorithmName);
////        matcher.setHashIterations(PasswordUtil.hashIterationCount);
////        matcher.setStoredCredentialsHexEncoded(PasswordUtil.storedCredentialsHexEncoded);
//        realm.setCredentialsMatcher(matcher);
//
//        return new CustomRealm();
//    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中-->安全管理器：securityManager可见securityManager是整个shiro的核心；
     */
//    @Bean
//    public EhCacheManager getEhCacheManager() {
//        System.out.println("ShiroConfig.getEhCacheManager()");
//        EhCacheManager cacheManager = new EhCacheManager();
//        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
//        return cacheManager;
//    }


//    /**
//     * Shiro生命周期处理器
//     * @return
//     */
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
