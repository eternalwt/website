package com.greengiant.website.config;

import com.greengiant.website.shiro.CustomRealm;
import com.greengiant.website.shiro.RetryLimitHashedCredentialsMatcher;
import com.greengiant.website.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean(SecurityManager securityManager) {
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
        shiroFilterFactoryBean.setFilterChainDefinitionMap(this.getfilterChainDefinitionMap());
        log.info("Shiro拦截器工厂类注入成功");

        return shiroFilterFactoryBean;
    }

    private Map<String, String> getfilterChainDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // todo 从数据库读取目录和权限对应关系【需要结合自己有多少个filter】。那么问题就变成：如何把功能和url对应起来（一个完善的路由机制）
        // todo 用一个表保存role和有权限的页面之间的对应关系，既用于加载页面，又用于鉴权。但是这样好像没用到permission表？再考虑一下
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

        return filterChainDefinitionMap;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm,
                                           CacheManager ehCacheCacheManager,
                                           CookieRememberMeManager rememberMeManager,
                                           DefaultWebSessionManager sessionManager) {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // 注入自定义的realm
        securityManager.setRealm(customRealm);
        // 注入缓存管理器
        securityManager.setCacheManager(ehCacheCacheManager);
        securityManager.setRememberMeManager(rememberMeManager);
        securityManager.setSessionManager(sessionManager);

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

    /**
     *Cookie
     */
    @Bean
    public SimpleCookie rememberMeCookie() {
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("REMEMBERCOOKIE");
        //如果httyOnly设置为true，则客户端不会暴露给客户端脚本代码，使用HttpOnly cookie有助于减少某些类型的跨站点脚本攻击；
        simpleCookie.setHttpOnly(true);
        // 生效时间,单位是秒
        simpleCookie.setMaxAge(60 * 60 * 24 * 30);
        return simpleCookie;
    }

    /**
     * cookie管理器;
     */
    @Bean
    public CookieRememberMeManager rememberMeManager(SimpleCookie rememberMeCookie) {
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        //rememberme cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度（128 256 512 位），通过以下代码可以获取
        //KeyGenerator keygen = KeyGenerator.getInstance("AES");
        //SecretKey deskey = keygen.generateKey();
        //System.out.println(Base64.encodeToString(deskey.getEncoded()));
        byte[] cipherKey = Base64.decode("wGiHplamyXlVB11UXWol8g==");
        cookieRememberMeManager.setCipherKey(cipherKey);
        cookieRememberMeManager.setCookie(rememberMeCookie);

        return cookieRememberMeManager;
    }

    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setGlobalSessionTimeout(180000);//单位毫秒
        //sessionManager.setSessionIdCookie();
        //sessionManager.setSessionIdCookieEnabled(true);

        return sessionManager;
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
