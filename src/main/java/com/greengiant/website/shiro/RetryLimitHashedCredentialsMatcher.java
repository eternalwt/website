package com.greengiant.website.shiro;

import net.sf.ehcache.CacheManager;
import org.apache.commons.collections.map.HashedMap;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {

    //todo 如果上多个服务需要注意全局性
//    private Cache<String, AtomicInteger> passwordRetryCache;

    //todo 后续用缓存处理 1.用注解的方式写通；2.用原生CacheManager的方式写通
    // 用注解的方式，我再维护一个map是没有意义的（对于需要操作数据库的再加方法才有意义）。尝试最终用CacheManager解决问题
    private static Map<String, Integer> cache = new HashedMap();

    private int maxRetryCount = 5;

    // todo 把shiro、ehcache、spring里面的缓存都学通，不是那么容易
    //private CacheManager cacheManager;

//    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager) {
//        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
//    }
//
//    public RetryLimitHashedCredentialsMatcher(CacheManager cacheManager, int maxRetryCount) {
//        passwordRetryCache = cacheManager.getCache("passwordRetryCache");
//        this.maxRetryCount = maxRetryCount;
//    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        //retry count + 1
//        AtomicInteger retryCount = passwordRetryCache.get(username);
//        if(retryCount == null) {
//            retryCount = new AtomicInteger(0);
//            passwordRetryCache.put(username, retryCount);
//        }
//        if(retryCount.incrementAndGet() > maxRetryCount) {
//            //todo 测试
//            // todo 重试的时候需要输入验证码
//            throw new ExcessiveAttemptsException("您已连续错误达" + maxRetryCount + "次！请N分钟后再试");
//        }

        int retryCount = 0;
        if (cache.get(username) != null) {
            retryCount = cache.get(username);
        }
        //if (retryCount.incrementAndGet() > 5) {
        if(retryCount++ > maxRetryCount) {
            //todo 测试
            // todo 重试的时候需要输入验证码
            throw new ExcessiveAttemptsException("您已连续错误达" + maxRetryCount + "次！请N分钟后再试");
        }

        //todo 父类实现了加密，测试一下
        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            //passwordRetryCache.remove(username);
            cache.remove(username);
        }
        else {
            throw new IncorrectCredentialsException("密码错误，已错误" + retryCount + "次，最多错误" + maxRetryCount + "次");
        }

        return matches;
    }
}