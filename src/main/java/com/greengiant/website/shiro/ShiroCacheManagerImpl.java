package com.greengiant.website.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ShiroCacheManagerImpl implements CacheManager {
    // todo "Shiro默认整合了EhCache，来实现缓存"，那么用ehcache的时候这个不需要注入【升级到ehcache3后需要再次确认】
    //  并且这个方案也是可以通用的【是可以验证的，也可以看EhCacheManager源码】

    /**
     * spring的CacheManager
     */
    @Autowired
    private org.springframework.cache.CacheManager springCacheManager;

    /**
     * set
     * @param springCacheManager
     */
    public void setSpringCacheManager(org.springframework.cache.CacheManager springCacheManager) {
        this.springCacheManager = springCacheManager;
    }

    /**
     * get
     * @return
     */
    public org.springframework.cache.CacheManager getSpringCacheManager() {
        return springCacheManager;
    }

    /**
     * 实现shiro的CacheManager中的方法，这里返回的Cache是shiro的Cache
     * @param name
     * @param <K>
     * @param <V>
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        return new ShiroCacheImpl<K, V>(name, springCacheManager);
    }

}
