package com.greengiant.website.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

/**
 * springboot CacheManager的包装类
 */
@Component
public class CacheManagerWrapper {

    @Autowired
    private CacheManager cacheManager;

    public Object getItem(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        Cache.ValueWrapper vw = cache.get(key);
        if (vw != null) {
            return vw.get();
        }

        return null;
    }

    public void putItem(String cacheName, String key, Object item) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            cache.put(key, item);
        }
    }

    public void updateItem(String cacheName, String key, Object value) {
        putItem(cacheName, key, value);
    }

    public boolean removeItem(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        if (cache != null) {
            return cache.evictIfPresent(key);
        }
        return false;
    }

}
