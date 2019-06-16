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
import org.apache.shiro.cache.CacheManager;
//import org.apache.shiro.cache.ehcache.EhCacheManager;
import org.apache.shiro.crypto.hash.DefaultHashService;
import org.apache.shiro.crypto.hash.Hash;
import org.apache.shiro.crypto.hash.HashRequest;
import org.apache.shiro.crypto.hash.HashService;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.util.LinkedHashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {

//    @Autowired
//    private EhCacheManager ehCacheManager;

    @Autowired
    private CustomRealm customRealm;

    @Bean
    public ShiroFilterFactoryBean shirFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        // 必须设置 SecurityManager
        shiroFilterFactoryBean.setSecurityManager(securityManager);
        // setLoginUrl 如果不设置值，默认会自动寻找Web工程根目录下的"/login.jsp"页面 或 "/login" 映射
        //todo 和后端action对应起来。success的url是什么时候用的？
        shiroFilterFactoryBean.setLoginUrl("/notLogin");//todo 配好静态页面
        shiroFilterFactoryBean.setSuccessUrl("loginSuccess");//todo 测试，应该是登录成功后跳转的页面吧
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
        filterChainDefinitionMap.put("/auth/**", "anon");
        //用户，需要角色权限 “user”
        filterChainDefinitionMap.put("/user/**", "anon");
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
        // 配置Realm、CacheManager、RememberMeManager、sessionManager
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
        // todo
        // 注入缓存管理器;
        // securityManager.setCacheManager(ehCacheManager());//todo 究竟写在这一行还是下面一行？
        // 注入自定义的realm
        securityManager.setRealm(customRealm);//customRealm() customRealmWithMatcher(getEhCacheManager())
        //securityManager.setSessionManager();
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
        // todo 改成@autowired
        realm.setCredentialsMatcher(getHashedCredentialsMatcher());

        return realm;
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
//        //todo
//        return defaultPasswordService;
//    }

    private CredentialsMatcher getHashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new RetryLimitHashedCredentialsMatcher();
        //todo 再写回去一次，要能快速反复来回配
        //HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        //加密方式
        hashedCredentialsMatcher.setHashAlgorithmName("md5");
        //加密次数
        hashedCredentialsMatcher.setHashIterations(2);
        //存储散列后的密码是否为16进制
        hashedCredentialsMatcher.setStoredCredentialsHexEncoded(PasswordUtil.storedCredentialsHexEncoded);

        return hashedCredentialsMatcher;
    }

//    //todo autowire
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

}
