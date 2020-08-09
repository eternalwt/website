package com.greengiant.website.utils;

import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

public class CacheUtil {

    private CacheUtil() {};

    public static Object getItem(CacheManager cacheManager, String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        Cache.ValueWrapper vw = cache.get(key);
        if (vw != null) {
            return vw.get();
        }

        return null;
    }

    public static void putItem(CacheManager cacheManager, String cacheName, String key, Object item) {
        Cache cache = cacheManager.getCache(cacheName);
        cache.put(key, item);
    }

    public static void updateItem(CacheManager cacheManager, String cacheName, String key, Object value) {
        putItem(cacheManager, cacheName, key, value);
    }

    public static boolean removeItem(CacheManager cacheManager, String cacheName, String key) {
        Cache cache = cacheManager.getCache(cacheName);
        return cache.evictIfPresent(key);
    }

//    void getKeys(CacheManager cacheManager, String cacheName) {
//        Cache cache = cacheManager.getCache(cacheName);
//    }

}
