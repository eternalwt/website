package com.greengiant.website.shiro;

import com.greengiant.website.cache.CacheManagerWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;

@Component
public class CustomCachedSessionDAO extends AbstractSessionDAO {
    // todo 看EnterpriseCacheSessionDAO代码，为啥CustomCacheSessionDAO不像EnterpriseCacheSessionDAO一样
    //  直接 extends CachingSessionDAO。是因为CachingSessionDAO里面的内容太过冗余？
    private static Logger LOGGER = LogManager.getLogger(ShiroRedisSessionDAO.class);

    /**
     * key前缀
     */
    private static final String SHIRO_REDIS_SESSION_KEY_PREFIX = "shiro.redis.session_";

    private static final String cacheName = "sessionCache";

    @Autowired
    private CacheManagerWrapper cacheManagerWrapper;

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro redis session create. sessionId={}", sessionId);
        }
        this.assignSessionId(session, sessionId);

        cacheManagerWrapper.putItem(cacheName, generateKey(sessionId), session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro redis session read. sessionId={}", sessionId);
        }
        return (Session) cacheManagerWrapper.getItem(cacheName, generateKey(sessionId));
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro redis session update. sessionId={}", session.getId());
        }
        cacheManagerWrapper.putItem(cacheName, generateKey(session.getId()), session);
    }

    @Override
    public void delete(Session session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro redis session delete. sessionId={}", session.getId());
        }
        cacheManagerWrapper.removeItem(cacheName, generateKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
//        Set<Object> keySet = redisTemplate.keys(generateKey("*"));
//        Set<Session> sessionSet = new HashSet<>();
//        if (CollectionUtils.isEmpty(keySet)) {
//            return Collections.emptySet();
//        }
//        for (Object key : keySet) {
//            sessionSet.add((Session) valueOperations.get(key));
//        }
//        if (LOGGER.isDebugEnabled()) {
//            LOGGER.debug("shiro redis session all. size={}", sessionSet.size());
//        }
//        return sessionSet;

        // todo
        return null;
    }

    /**
     * 重组key
     * 区别其他使用环境的key
     *
     * @param key
     * @return
     */
    private String generateKey(Object key) {
//        return SHIRO_REDIS_SESSION_KEY_PREFIX + this.getClass().getName() + "_" + key;
        return SHIRO_REDIS_SESSION_KEY_PREFIX + "_" + key;
    }
}