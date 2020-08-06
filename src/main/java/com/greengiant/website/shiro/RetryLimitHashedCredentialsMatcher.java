package com.greengiant.website.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;


public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    // todo 把AtomicInteger写进去，看AtomicInteger源码
    @Value("${password.max-retry-count}")
    private int MAX_RETRY_COUNT;

    private static final String cacheName = "passwordRetryCache";

//    @Autowired // todo 怎么理顺这个告警很重要。为啥这里虽然有告警，但是确实可以autowire？
//    // todo 在现在的配置下，这里还是用的ehcache。注释掉ehcache后把redis版的写通
//    // todo 能否作为参数传入？
//    private CacheManager cacheManager; // todo 类上面没有注解，成员也是可以autowired的。1.搞懂扫描过程；2.这样会不会导致代码混乱不好维护？

    private CacheManager cacheManager;

    public void setCacheManager(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        if(this.getItem(username) != null && Integer.parseInt(this.getItem(username).toString()) >= MAX_RETRY_COUNT) {
            // todo 重试的时候需要输入验证码
            throw new ExcessiveAttemptsException("您已连续输错" + MAX_RETRY_COUNT + "次密码！请30分钟后再试");// todo 30根据配置来写
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            this.removeItem(username);
        }
        else {
            if(this.getItem(username) == null) {
                this.putItem(username, 1);
                throw new IncorrectCredentialsException("密码错误");
            }
            else {
                this.updateItem(username, Integer.parseInt(this.getItem(username).toString()) + 1);
                throw new IncorrectCredentialsException("密码错误，已连续错误" + (this.getItem(username).toString())
                        + "次，连续错误" + MAX_RETRY_COUNT + "次账号将被锁定");
            }
        }

        return matches;
    }

    private Object getItem(String key) {
        Cache passwordRetryCache = cacheManager.getCache(cacheName);
        Cache.ValueWrapper vw = passwordRetryCache.get(key);
        if (vw != null) {
            return vw.get();
        }

        return null;
    }

    private void putItem(String key, Object item) {
        Cache passwordRetryCache = cacheManager.getCache(cacheName);
        passwordRetryCache.put(key, item);
    }

    private void updateItem(String key, Object value) {
        putItem(key, value);
    }

    public boolean removeItem(String key) {
        Cache passwordRetryCache = cacheManager.getCache(cacheName);
        return passwordRetryCache.evictIfPresent(key);
    }

}