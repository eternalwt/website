package com.greengiant.website.shiro;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.apache.shiro.session.mgt.eis.CachingSessionDAO;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Component
public class CustomCachedSessionDAO extends CachingSessionDAO {

    private static Logger LOGGER = LogManager.getLogger(CustomCachedSessionDAO.class);

    /**
     * key前缀
     */
    private static final String SHIRO_SESSION_CACHE_KEY_PREFIX = "shiro.session_";

    private static final String cacheName = "sessionCache";

    @Override
    protected Serializable doCreate(Session session) {
        Serializable sessionId = this.generateSessionId(session);
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro custom session create. sessionId={}", sessionId);
        }
        this.assignSessionId(session, sessionId);

        this.getCacheManager().getCache(cacheName).put(generateKey(sessionId), session);
        return sessionId;
    }

    @Override
    protected Session doReadSession(Serializable sessionId) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro custom session read. sessionId={}", sessionId);
        }
        return (Session)this.getCacheManager().getCache(cacheName).get(generateKey(sessionId));
    }

    @Override
    public void doUpdate(Session session) throws UnknownSessionException {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro custom session update. sessionId={}", session.getId());
        }
        this.getCacheManager().getCache(cacheName).put(session.getId(), session);
    }

    @Override
    public void doDelete(Session session) {
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("shiro custom session delete. sessionId={}", session.getId());
        }
        this.getCacheManager().getCache(cacheName).remove(generateKey(session.getId()));
    }

    @Override
    public Collection<Session> getActiveSessions() {
        List<Session> list = new ArrayList<>();
        for (Object obj : this.getCacheManager().getCache(cacheName).values()) {
            list.add((Session)obj);
        }

        return list;
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
        return SHIRO_SESSION_CACHE_KEY_PREFIX + "_" + key;
    }

}
