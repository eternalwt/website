package com.greengiant.website;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.StringRedisConnection;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RedisTests {

    @Autowired
    RedisTemplate<String, String> redisTemplate;

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testRedisTemplate() {
        // todo 有4个executePipelined方法的重载
        // todo 测试set集合
//        redisTemplate.opsForValue().set("ttt", "kkk");
        stringRedisTemplate.opsForValue().set("ttt2", "kkk2");
        String val = stringRedisTemplate.opsForValue().get("ttt2");
        System.out.println(val);

        List<Object> sessionCallback = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            // todo 我感觉这里的K,V只是一个占位的，类型由stringRedisTemplate传过来的<String, String>，确认一下展开过程
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                // todo 搞清楚这里的K V怎么具体化
                //                redisOperations.boundSetOps("ttt2").add("ttt3");

                return null;
            }
        });

        List<Object> sessionCallback2 = redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            // todo 我感觉这里的K,V只是一个占位的，类型由redisTemplate传过来的，确认一下展开过程
            public <K, V> Object execute(RedisOperations<K, V> redisOperations) throws DataAccessException {
                //                redisOperations.boundSetOps("ttt2").add("ttt3");

                return null;
            }
        });

        // todo 3.webUploader怎么测试？
//        List<Object> sessionCallback = stringRedisTemplate.executePipelined(new SessionCallback<Object>() {
//            @Override
//            public Object execute(RedisOperations redisOperations) throws DataAccessException {
//                redisOperations.boundSetOps("ttt3").add("kkk4");
//                redisOperations.boundSetOps("ttt3").size();
//                return null;
//            }
//        });
//
//        if (sessionCallback.size() == 2) {
//            Long count = Long.parseLong(sessionCallback.get(1).toString());
//            System.out.println(count);
//            System.out.println(sessionCallback.size());
//        }

        List<Object> results = stringRedisTemplate.executePipelined((RedisCallback<Object>) connection -> {
            StringRedisConnection stringRedisConn = (StringRedisConnection)connection;
//				stringRedisConn.openPipeline();
            stringRedisConn.sAdd("ttt3", "kkk4");
            stringRedisConn.sAdd("ttt3", "kkk7");
            stringRedisConn.sCard("ttt3");
            stringRedisConn.close();// todo 是否需要释放资源，应该不需要
            return null;
        });

        System.out.println("finished...");
    }

}
