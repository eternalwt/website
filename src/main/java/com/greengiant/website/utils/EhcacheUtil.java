package com.greengiant.website.utils;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import net.sf.ehcache.config.CacheConfiguration;
import net.sf.ehcache.store.MemoryStoreEvictionPolicy;

//public class EhcacheUtil {
//    private static final CacheManager cacheManager = CacheManager.getInstance();
//    //public static Cache passwordRetryCache;
//
//    /**
//     * 创建ehcache缓存，创建之后的有效期是1小时
//     */
//    // todo 和xml配置文件的关系？
//    private static Cache passwordRetryCache = new Cache(new CacheConfiguration("passwordRetryCache", 5000)
//            .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO)
//            .timeoutMillis(300)
//            .timeToLiveSeconds(60 * 60));
//
//    static {
//        cacheManager.addCache(passwordRetryCache);
//    }
//
//    public static void putItem(String key, Object item) {
//        if (passwordRetryCache.get(key) != null) {
//            passwordRetryCache.remove(key);
//        }
//        Element element = new Element(key, item);
//        passwordRetryCache.put(element);
//    }
//
//    public static void removeItem(String key) {
//        passwordRetryCache.remove(key);
//    }
//
//    public static void updateItem(String key, Object value) {
//        putItem(key, value);
//    }
//
//    public static Object getItem(String key) {
//        Element element=  passwordRetryCache.get(key);
//        if(null!=element)
//        {
//            return element.getObjectValue();
//        }
//        return null;
//    }
//
//}



public class EhcacheUtil {
    private static final CacheManager cacheManager = CacheManager.getInstance();
    //public static Cache passwordRetryCache;

    /**
     * 创建ehcache缓存，创建之后的有效期是1小时
     */
    // todo 和xml配置文件的关系？
    private static Cache passwordRetryCache = new Cache(new CacheConfiguration("passwordRetryCache", 5000)
            .memoryStoreEvictionPolicy(MemoryStoreEvictionPolicy.FIFO)
            .timeoutMillis(300)
            .timeToLiveSeconds(60 * 60));

    static {
        cacheManager.addCache(passwordRetryCache);
    }

    public static void putItem(String key, Object item) {
        if (passwordRetryCache.get(key) != null) {
            passwordRetryCache.remove(key);
        }
        Element element = new Element(key, item);
        passwordRetryCache.put(element);
    }

    public static void removeItem(String key) {
        passwordRetryCache.remove(key);
    }

    public static void updateItem(String key, Object value) {
        putItem(key, value);
    }

    public static Object getItem(String key) {
        Element element=  passwordRetryCache.get(key);
        if(null!=element)
        {
            return element.getObjectValue();
        }
        return null;
    }

}