package com.greengiant.website.config;

import com.greengiant.website.shiro.CustomCachedSessionDAO;
import com.greengiant.website.shiro.CustomRealm;
import com.greengiant.website.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
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
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射【看一下代码】
        //todo 和后端action对应起来。success的url是什么时候用的？
//        shiroFilterFactoryBean.setLoginUrl("/notLogin.html"); // 点击没有权限的菜单项是可以触发的，再测一下。对应到login页面
        shiroFilterFactoryBean.setLoginUrl("/notLogin.html");
        //todo 测试，应该是登录成功后跳转的页面吧
        // todo 有用吗？
        shiroFilterFactoryBean.setSuccessUrl("loginSuccess");
        // 设置无权限时跳转的 url;
        // todo 下面的用法是不是错了，是不是应该结合后端方法（例如notLogin）来处理？
        shiroFilterFactoryBean.setUnauthorizedUrl("http://localhost:4200/login");
        // 设置拦截器
        shiroFilterFactoryBean.setFilterChainDefinitionMap(this.getfilterChainDefinitionMap());
        log.info("Shiro拦截器工厂类注入成功");

        return shiroFilterFactoryBean;
    }

    private Map<String, String> getfilterChainDefinitionMap() {
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        // todo 运维，调试完后应该加上限制
        // todo swagger路径用2个*配置也不行，再思考一下
        // todo 这里能否拿到数据库管理？
        filterChainDefinitionMap.put("/websocket/**", "anon");
        filterChainDefinitionMap.put("/menu/**", "anon");
        filterChainDefinitionMap.put("/permission/**", "anon");
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
        filterChainDefinitionMap.put("/admin/**", "roles[admin]");
        // 其余接口一律拦截（这行代码必须放在所有权限设置的最后，不然会导致所有 url 都被拦截）
        filterChainDefinitionMap.put("/**", "user");

        return filterChainDefinitionMap;
    }

    /**
     * 注入 securityManager
     */
    @Bean
    public SecurityManager securityManager(CustomRealm customRealm,
                                           HashedCredentialsMatcher hashedCredentialsMatcher,
                                           CacheManager shiroCacheManager,
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
        securityManager.setRememberMeManager(rememberMeManager);
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

//    @Bean
//    public HashedCredentialsMatcher hashedCredentialsMatcher(CacheManager cacheManager) {
//        RetryLimitHashedCredentialsMatcher retryLimitHashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();
//        //hash算法
//        retryLimitHashedCredentialsMatcher.setHashAlgorithmName(PasswordUtil.ALGORITHM_NAME);
//        //加密次数
//        retryLimitHashedCredentialsMatcher.setHashIterations(PasswordUtil.HASH_ITERATION_COUNT);
//        //存储散列后的密码是否为16进制
//        retryLimitHashedCredentialsMatcher.setStoredCredentialsHexEncoded(PasswordUtil.STORED_CREDENTIALS_HEX_ENCODED);
//
//        return retryLimitHashedCredentialsMatcher;
//    }

//    /**
//     * 配置shiro redisManager
//     * 使用的是shiro-redis开源插件
//     *
//     * @return
//     */
//    @Bean
//    public RedisManager redisManager() {
////        RedisManager redisManager = new MyRedisManager();
//        RedisManager redisManager = new RedisManager();
//        redisManager.setHost("localhost:6379");
////        redisManager.set
////        redisManager.setPort(6379);
////        // 配置缓存过期时间
////        redisManager.setExpire(expireTime);
////        redisManager.setTimeout(timeOut);
//        // redisManager.setPassword(password);
//        return redisManager;
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
    public DefaultWebSessionManager sessionManager(CacheManager cacheManager,
                                                   AbstractSessionDAO customCachedSessionDAO) {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //单位毫秒，1小时后失效
        sessionManager.setGlobalSessionTimeout(1000 * 60 * 60);
        // 相隔多久检查一次session的有效性
        sessionManager.setSessionValidationInterval(1000 * 60 * 15);
        // 删除失效session
        sessionManager.setDeleteInvalidSessions(true);

//        customCachedSessionDAO.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
        sessionManager.setSessionDAO(customCachedSessionDAO);

        // todo 调查这里是否还需要设置。sessionDAO也设置了缓存，关系是啥？？不是说只设置一个地方就可以了吗？
        sessionManager.setCacheManager(cacheManager);

        return sessionManager;
    }

//    @Bean(name = "sessionDao")
//    public EnterpriseCacheSessionDAO sessionDao(){
//        EnterpriseCacheSessionDAO sessionDao = new EnterpriseCacheSessionDAO();
////        sessionDao.setActiveSessionsCacheName("shiro-activeSessionCache");
//        sessionDao.setSessionIdGenerator(new JavaUuidSessionIdGenerator());
//        return sessionDao;
//    }

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
