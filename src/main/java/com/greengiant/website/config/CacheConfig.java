package com.greengiant.website.config;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class CacheConfig {

//    @Bean
//    public EhCacheManagerFactoryBean ehCacheManagerFactoryBean(){
//        EhCacheManagerFactoryBean cacheManagerFactoryBean = new EhCacheManagerFactoryBean();
//        cacheManagerFactoryBean.setConfigLocation(new ClassPathResource("ehcache.xml")); // todo 这里是不是和yml里面重复了？
//        cacheManagerFactoryBean.setShared(true);
//        return cacheManagerFactoryBean;
//    }

//    /**
//     * ehcache 主要的管理器
//     */
//    @Bean // (name = "appEhCacheCacheManager")
//    public EhCacheCacheManager ehCacheCacheManager(EhCacheManagerFactoryBean bean){
//        return new EhCacheCacheManager(bean.getObject());
//    }

    // todo 1.这里把shiro和springboot的CacheManager的实现都配一下，配redis（或者ehcache）
    //  springboot的我自定义matcher等地方用到
    //  【流程是从这里一步步往下配，让后面跟这里保持一致】



}
