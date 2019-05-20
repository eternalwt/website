package com.greengiant.website.shiro;

import java.util.concurrent.atomic.AtomicInteger;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheManager;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    private Cache<String, AtomicInteger> passwordRetryCache;

    private int maxRetryCount = 5;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
    }

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager, int maxRetryCount) {
        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
        this.maxRetryCount = maxRetryCount;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
        AtomicInteger retryCount = passwordRetryCache.get(username);
        if(retryCount == null) {
            retryCount = new AtomicInteger(0);
            passwordRetryCache.put(username, retryCount);
        }
        if(retryCount.incrementAndGet() > maxRetryCount) {
            //todo 测试
            throw new ExcessiveAttemptsException("您已连续错误达" + maxRetryCount + "次！请N分钟后再试");
        }

        //todo 父类实现了加密，测试一下
        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            passwordRetryCache.remove(username);
        }
        else {
            throw new IncorrectCredentialsException("密码错误，已错误" + retryCount + "次，最多错误" + 5 + "次");
        }

        return matches;
    }
}