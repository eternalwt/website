package com.greengiant.website.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

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
        cache.put(key, item);
    }

    public void updateItem(String cacheName, String key, Object value) {
        putItem(cacheName, key, value);
    }

    public boolean removeItem(String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        return cache.evictIfPresent(key);
    }

//    void getKeys(CacheManager cacheManager, String cacheName) {
//        Cache cache = cacheManager.getCache(cacheName);
//    }

}
