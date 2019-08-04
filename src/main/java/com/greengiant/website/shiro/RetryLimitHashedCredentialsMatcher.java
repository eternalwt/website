package com.greengiant.website.shiro;

import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

@Component
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    //todo 如果上多个服务需要注意全局性
    public CacheManager cacheManager;

    Cache passwordRetryCache;

    //todo 后续用缓存处理 1.用注解的方式写通；2.用原生CacheManager的方式写通
    // 用注解的方式，我再维护一个map是没有意义的（对于需要操作数据库的再加方法才有意义）。尝试最终用CacheManager解决问题
    // todo 把shiro、ehcache、spring里面的缓存都学通，不是那么容易。把AtomicInteger写进去

    private int MAX_RETRY_COUNT = 5;

    public RetryLimitHashedCredentialsMatcher() {
    }

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        passwordRetryCache = cacheManager.getCache("passwordRetry");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();

        if(passwordRetryCache.get(username) != null && Integer.valueOf(passwordRetryCache.get(username).get().toString()) >= MAX_RETRY_COUNT) {
            // todo 重试的时候需要输入验证码
            //todo 30读配置文件
            throw new ExcessiveAttemptsException("您已连续输错" + MAX_RETRY_COUNT + "次密码！请30分钟后再试");
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            passwordRetryCache.evict(username);
        }
        else {
            if(passwordRetryCache.get(username) == null) {
                passwordRetryCache.put(username, 0);
                throw new IncorrectCredentialsException("密码错误");
            }
            else {
                passwordRetryCache.put(username, Integer.valueOf(passwordRetryCache.get(username).get().toString()) + 1);
                throw new IncorrectCredentialsException("密码错误，已连续错误" + (passwordRetryCache.get(username).get().toString()) + "次，最多连续错误" + MAX_RETRY_COUNT + "次");
            }
        }

        return matches;
    }

}