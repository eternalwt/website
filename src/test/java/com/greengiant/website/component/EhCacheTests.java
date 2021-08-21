package com.greengiant.website.component;

import com.greengiant.website.WebsiteApplication;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class EhCacheTests {

    @Before
    public void testBefore(){
        System.out.println("测试前");
    }

    @Test
    public void test001() {

    }

//    private static final CacheManager cacheManager = CacheManager.create("src/main/resources/ehcache.xml");
//
//    /**
//     * 创建ehcache缓存，创建之后的有效期是1小时
//     */
//    private static Cache passwordRetryCache = cacheManager.getCache("passwordRetryCache");
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
//    public static void putItem(String key, Object item) {
//        if (passwordRetryCache.get(key) != null) {
//            passwordRetryCache.remove(key);
//        }
//        Element element = new Element(key, item);
//        passwordRetryCache.put(element);
//    }
//
//    public static void updateItem(String key, Object value) {
//        putItem(key, value);
//    }
//
//    public static boolean removeItem(String key) {
//        return passwordRetryCache.remove(key);
//    }

}
