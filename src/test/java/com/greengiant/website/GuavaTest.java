package com.greengiant.website;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.junit.Test;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.sleep;

public class GuavaTest {

    public static final Cache<String, String> cacheBuilder =
            CacheBuilder.newBuilder().expireAfterWrite(10, TimeUnit.SECONDS).build();

    @Test
    public void testExpire() throws InterruptedException, ExecutionException {
//        ZipUtil.adjustToLong();
        cacheBuilder.put("aaa", "t");
        sleep(5000);
        cacheBuilder.put("bbb", "f");
        sleep(6000);

        System.out.println("check if present");
        System.out.println(cacheBuilder.getIfPresent("aaa") == null);
        System.out.println(cacheBuilder.getIfPresent("bbb"));

        cacheBuilder.invalidate("bbb");
        System.out.println(cacheBuilder.getIfPresent("bbb"));

        System.out.println(cacheBuilder.get("bbb", new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "ccc";
            }
        }));

    }

}
