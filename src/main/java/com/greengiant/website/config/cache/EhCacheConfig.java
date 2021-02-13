package com.greengiant.website.config.cache;


import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableCaching
public class EhCacheConfig {

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

    // todo 1.这里加一个对spring的cacheManager的Bean实现
    //  2.然后确认一下是不是就可以无缝切换了

}
