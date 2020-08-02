package com.greengiant.website.shiro;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;

//@Component todo 再思考一下用component的用法
public class RetryLimitHashedCredentialsMatcher extends HashedCredentialsMatcher {
    // todo 能否把EhcacheUtil干掉，用注解？https://blog.csdn.net/tanleijin/article/details/81118963
    // todo 把AtomicInteger写进去，看AtomicInteger源码
    @Value("${password.max-retry-count}")
    private int MAX_RETRY_COUNT;

    private static final String cacheName = "passwordRetryCache";

    @Autowired // todo 怎么理顺这个告警很重要
    // todo 在现在的配置下，这里还是用的ehcache。注释掉ehcache后把redis版的写通
    // todo 能否作为参数传入？
    private CacheManager cacheManager; // todo 类上面没有注解，成员也是可以autowired的。1.搞懂扫描过程；2.这样会不会导致代码混乱不好维护？

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
                        + "次，最多连续错误" + MAX_RETRY_COUNT + "次");
            }
        }

        return matches;
    }

    private Object getItem(String key) {
        Cache passwordRetryCache = cacheManager.getCache(cacheName);
        Element element=  passwordRetryCache.get(key);
        if(null!=element)
        {
            return element.getObjectValue();
        }
        return null;
    }

    private void putItem(String key, Object item) {
        Cache passwordRetryCache = cacheManager.getCache(cacheName);

        if (passwordRetryCache.get(key) != null) {
            passwordRetryCache.remove(key);
        }
        Element element = new Element(key, item);
        passwordRetryCache.put(element);
    }

    private void updateItem(String key, Object value) {
        putItem(key, value);
    }

    public boolean removeItem(String key) {
        Cache passwordRetryCache = cacheManager.getCache(cacheName);
        return passwordRetryCache.remove(key);
    }

}