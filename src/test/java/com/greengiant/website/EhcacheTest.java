package com.greengiant.website;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

public class EhcacheTest {
    private static final CacheManager cacheManager = CacheManager.create("src/main/resources/ehcache.xml");

    /**
     * 创建ehcache缓存，创建之后的有效期是1小时
     */
    private static Cache passwordRetryCache = cacheManager.getCache("passwordRetryCache");

    public static Object getItem(String key) {
        Element element=  passwordRetryCache.get(key);
        if(null!=element)
        {
            return element.getObjectValue();
        }
        return null;
    }

    public static void putItem(String key, Object item) {
        if (passwordRetryCache.get(key) != null) {
            passwordRetryCache.remove(key);
        }
        Element element = new Element(key, item);
        passwordRetryCache.put(element);
    }

    public static void updateItem(String key, Object value) {
        putItem(key, value);
    }

    public static boolean removeItem(String key) {
        return passwordRetryCache.remove(key);
    }
}
