package com.greengiant.website.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Component
public class ShiroCacheManagerImpl implements CacheManager {

    /**
     * springboot的CacheManager
     */
    @Autowired
    private org.springframework.cache.CacheManager springCacheManager;

//    @Autowired
//    private org.springframework.cache.Cache cache;

    private final ConcurrentMap<String, Cache> cacheMap = new ConcurrentHashMap(16);

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
        if (cacheMap.get(name) != null) {
            return cacheMap.get(name);
        } else {
            Cache shiroCache = new ShiroCacheImpl<K, V>(name, springCacheManager);
            cacheMap.put(name, shiroCache);
            return shiroCache;
        }
    }

}
