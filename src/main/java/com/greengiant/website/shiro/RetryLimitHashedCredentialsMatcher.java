package com.greengiant.website.shiro;

import com.greengiant.website.cache.CacheManagerWrapper;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    // todo 把AtomicInteger写进去，看AtomicInteger源码
    @Value("${password.max-retry-count}")
    private int MAX_RETRY_COUNT;

    private static final String cacheName = "passwordRetryCache";

    @Autowired
    private CacheManagerWrapper cacheManagerWrapper;

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String)token.getPrincipal();
        if(cacheManagerWrapper.getItem(cacheName, username) != null &&
                Integer.parseInt(cacheManagerWrapper.getItem(cacheName, username).toString()) >= MAX_RETRY_COUNT) {
            // todo 重试的时候需要输入验证码
            throw new ExcessiveAttemptsException("您已连续输错" + MAX_RETRY_COUNT + "次密码！请30分钟后再试");// todo 30根据配置来写
        }

        boolean matches = super.doCredentialsMatch(token, info);
        if(matches) {
            //clear retry count
            cacheManagerWrapper.removeItem(cacheName, username);
        }
        else {
            if(cacheManagerWrapper.getItem(cacheName, username) == null) {
                cacheManagerWrapper.putItem(cacheName, username, 1);
                throw new IncorrectCredentialsException("密码错误");
            }
            else {
                cacheManagerWrapper.updateItem(cacheName, username,
                        Integer.parseInt(cacheManagerWrapper.getItem(cacheName, username).toString()) + 1);
                throw new IncorrectCredentialsException("密码错误，已连续错误" + (cacheManagerWrapper.getItem(cacheName, username).toString())
                        + "次，连续错误" + MAX_RETRY_COUNT + "次账号将被锁定");
            }
        }

        return matches;
    }

}