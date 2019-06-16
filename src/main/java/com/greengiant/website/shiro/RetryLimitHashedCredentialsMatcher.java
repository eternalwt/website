package com.greengiant.website.shiro;

import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    //todo 如果上多个服务需要注意全局性
    public CacheManager cacheManager;

    Cache cache;

    //todo 后续用缓存处理 1.用注解的方式写通；2.用原生CacheManager的方式写通
    // 用注解的方式，我再维护一个map是没有意义的（对于需要操作数据库的再加方法才有意义）。尝试最终用CacheManager解决问题
    // todo 把shiro、ehcache、spring里面的缓存都学通，不是那么容易。把AtomicInteger写进去

    private int MAX_RETRY_COUNT = 5;

    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
        cache = cacheManager.getCache("passwordRetry");
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
//        AtomicInteger retryCount = passwordRetryCache.get(username);
//        if(retryCount == null) {
//            retryCount = new AtomicInteger(0);
//            passwordRetryCache.put(username, retryCount);
//        }
//        if(retryCount.incrementAndGet() > MAX_RETRY_COUNT) {
//            //todo 测试
//            // todo 重试的时候需要输入验证码
//            throw new ExcessiveAttemptsException("您已连续错误达" + MAX_RETRY_COUNT + "次！请N分钟后再试");
//        }

        if(cache.get(username) != null && Integer.valueOf(cache.get(username).get().toString()) >= MAX_RETRY_COUNT) {
            // todo 重试的时候需要输入验证码
            //todo 30读配置文件
            throw new ExcessiveAttemptsException("您已连续输错" + MAX_RETRY_COUNT + "次密码！请30分钟后再试");
        }

        //todo 父类实现了加密，测试一下
        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            cache.evict(username);
        }
        else {
            if(cache.get(username) == null) {
                cache.put(username, 0);
                throw new IncorrectCredentialsException("密码错误");
            }
            else {
                cache.put(username, Integer.valueOf(cache.get(username).get().toString()) + 1);
                throw new IncorrectCredentialsException("密码错误，已连续错误" + (cache.get(username).get().toString()) + "次，最多连续错误" + MAX_RETRY_COUNT + "次");
            }
        }

        return matches;
    }

}