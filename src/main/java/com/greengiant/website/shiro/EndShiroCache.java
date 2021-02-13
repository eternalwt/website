package com.greengiant.website.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.cache.CacheException;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Slf4j
public class EndShiroCache<K, V>  implements org.apache.shiro.cache.Cache<K, V> {

    //cache中key的前缀
    private String cachePrefix = "shiro:cache:";

    //定义spring的缓存管理器
    private CacheManager springCacheManager;

    //定义spring的缓存
    private Cache cache;

    /**
     * 对cache中的key进行处理，加上前缀
     * @param k
     * @return
     */
    private K getKey(K k) {
        return (K) (cachePrefix + (k == null ? "*" : k));
    }

    /**
     * 通过构造方法来对spring的CacheManager和Cache进行初始化
     * @param name
     * @param springCacheManager
     */
    public EndShiroCache(String name, CacheManager springCacheManager) {
        this.springCacheManager = springCacheManager;
        this.cache = springCacheManager.getCache(name);
    }

    /**
     * 实现shiro的Cache中的get方法
     * @param k
     * @return
     * @throws CacheException
     */
    @Override
    public V get(K k) throws CacheException {
        log.warn("从缓存中获取key：{}", k);
        //调用spring的Cache的get方法
        Cache.ValueWrapper valueWrapper = cache.get(getKey(k));
        if (valueWrapper == null) {
            return null;
        }

        return (V) valueWrapper.get();
    }

    /**
     * 实现shiro的Cache中的put方法
     * @param k
     * @param v
     * @return
     * @throws CacheException
     */
    @Override
    public V put(K k, V v) throws CacheException {
        log.warn("将key：{}存入缓存", k);
        //调用spring的Cache的put方法
        cache.put(getKey(k), v);

        return v;
    }

    /**
     * 实现shiro的Cache中的remove方法
     * @param k
     * @return
     * @throws CacheException
     */
    @Override
    public V remove(K k) throws CacheException {
        log.warn("将key：{}从缓存中删除", k);
        V v = get(k);
        //调用spring的Cache的evict方法
        cache.evict(getKey(k));

        return v;
    }

    /**
     * 实现shiro的Cache中的clear方法
     * @throws CacheException
     */
    @Override
    public void clear() throws CacheException {
        log.warn("清空name:{}的缓存", cache.getName());
        //调用spring的Cache的clear方法
        cache.clear();
    }

    /**
     * 实现shiro的Cache中的size方法
     * @return
     */
    @Override
    public int size() {
        int size = keys().size();
        log.warn("获取name：{}的cache的size：{}", cache.getName(), size);

        return size;
    }

    /**
     * 实现shiro的Cache中的keys方法
     * @return
     */
    @Override
    public Set<K> keys() {
        //调用spring的CacheManager的getCacheNames方法
        return (Set<K>) springCacheManager.getCacheNames();
    }

    /**
     * 实现shiro的Cache中的values方法
     * @return
     */
    @Override
    public Collection<V> values() {
        List<V> list = new ArrayList<>();
        Set<K> keys = keys();
        for(K k : keys) {
            list.add(get(k));
        }

        return list;
    }
}
