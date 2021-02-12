package com.greengiant.website.shiro;

import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

public class EndShiroCacheManager implements CacheManager {
    // todo 这个不叫manager，应该叫impl。改名ShiroRedisCacheManagerImpl（两个类合成为一个）

    /**
     * spring的CacheManager
     */
    private org.springframework.cache.CacheManager springCacheManager;

    /**
     * 实现shiro的CacheManager中的方法，
     * 这里返回的Cache当然是shiro的Cache
     * @param name
     * @param <K>
     * @param <V>
     * @return
     * @throws CacheException
     */
    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        //这里EndShiroCache是shiro的Cache的实现类
        return new EndShiroCache<K, V>(name, getSpringCacheManager());
    }

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
}
