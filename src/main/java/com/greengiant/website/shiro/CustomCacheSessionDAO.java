package com.greengiant.website.shiro;

import com.greengiant.website.utils.CacheUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.AbstractSessionDAO;
import org.springframework.cache.CacheManager;

import java.io.Serializable;
import java.util.Collection;

public class CustomCacheSessionDAO extends AbstractSessionDAO {
    // todo 看EnterpriseCacheSessionDAO代码，为啥不像它一样直接 extends CachingSessionDAO

    private static Logger LOGGER = LogManager.getLogger(ShiroRedisSessionDAO.class);

    /**
     * key前缀
     */
    private static final String SHIRO_REDIS_SESSION_KEY_PREFIX = "shiro.redis.session_";

    private static final String cacheName = "sessionCache";

    private CacheManager cacheManager;

    public CustomCacheSessionDAO(CacheManager cacheManager) {
        this.cacheManager = cacheManager;
    }

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro redis session create. sessionId={}", sessionId);
        }
        this.assignSessionId(session, sessionId);

        CacheUtil.putItem(cacheManager, cacheName, generateKey(sessionId), session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro redis session read. sessionId={}", sessionId);
        }
        return (Session) CacheUtil.getItem(cacheManager, cacheName, generateKey(sessionId));
    }

    @Override
    public void update(Session session) throws UnknownSessionException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro redis session update. sessionId={}", session.getId());
        }
        CacheUtil.putItem(cacheManager, cacheName, generateKey(session.getId()), session);
    }

    @Override
    public void delete(Session session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro redis session delete. sessionId={}", session.getId());
        }
        CacheUtil.removeItem(cacheManager, cacheName, generateKey(session.getId()));
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