package com.greengiant.website.config;

import com.greengiant.website.shiro.CustomRealm;
import com.greengiant.website.shiro.RetryLimitHashedCredentialsMatcher;
import com.greengiant.website.utils.PasswordUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.CacheManager;
import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.apache.shiro.mgt.SecurityManager;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {
    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        //todo 和后端action对应起来。success的url是什么时候用的？
        shiroFilterFactoryBean.setLoginUrl("/notLogin");
        shiroFilterFactoryBean.setSuccessUrl("loginSuccess");
        // 设置无权限时跳转的 url;
        shiroFilterFactoryBean.setUnauthorizedUrl("/notRole");

        // 设置拦截器
        // todo 如何动态配置拦截器？
        //todo 对比思考我在威盛电子时做的细粒度权限管理
        Map<String, String> filterChainDefinitionMap = new LinkedHashMap<>();
        //todo 运维，调试完后应该加上限制
        //todo 用2个*配置也不行，再思考一下
        filterChainDefinitionMap.put("/swagger**", "anon");
        filterChainDefinitionMap.put("/actuator/**", "anon");
        filterChainDefinitionMap.put("/static/**", "anon");
        //游客，开发权限
        filterChainDefinitionMap.put("/guest/**", "anon");
        //开放登陆接口
        filterChainDefinitionMap.put("/login", "anon");
        //用户，需要角色权限 “user”
        filterChainDefinitionMap.put("/user/**", "roles[user]");
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
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // todo
        // 注入缓存管理器;
        // securityManager.setCacheManager(ehCacheManager());
        // 注入自定义的realm
        securityManager.setRealm(customRealm());
        // todo
        //securityManager.setRememberMeManager();

        return securityManager;
    }

    /**
     * 自定义身份认证 realm;
     * <p>
     * 必须写这个类，并加上 @Bean 注解，目的是注入 CustomRealm，
     * 否则会影响 CustomRealm类 中其他类的依赖注入
     */
    @Bean
    public CustomRealm customRealm() {
        CustomRealm realm = new CustomRealm();
        //realm.setCacheManager();
        //realm.setCachingEnabled();
        // todo
        //RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher();
        //realm.setCredentialsMatcher();

        return new CustomRealm();
    }

    @Bean(name="customRealmWithMatcher")
    public CustomRealm customRealmWithMatcher(CacheManager cacheManager) {
        CustomRealm realm = new CustomRealm();
        //realm.setCacheManager();
        //realm.setCachingEnabled();
        //RetryLimitHashedCredentialsMatcher matcher = new RetryLimitHashedCredentialsMatcher(cacheManager);
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName(PasswordUtil.algorithmName);
        matcher.setHashIterations(PasswordUtil.hashIterationCount);
        matcher.setStoredCredentialsHexEncoded(PasswordUtil.storedCredentialsHexEncoded);
        realm.setCredentialsMatcher(matcher);

        return new CustomRealm();
    }

    /**
     * shiro缓存管理器;
     * 需要注入对应的其它的实体类中-->安全管理器：securityManager可见securityManager是整个shiro的核心；
     */
    @Bean
    public EhCacheManager ehCacheManager() {
        System.out.println("ShiroConfiguration.getEhCacheManager()");
        EhCacheManager cacheManager = new EhCacheManager();
        cacheManager.setCacheManagerConfigFile("classpath:ehcache.xml");
        return cacheManager;
    }

}
