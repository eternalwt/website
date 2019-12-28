package com.greengiant.website;

import junit.framework.TestCase;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class DemoApplicationTests {

    @Before
    public void testBefore(){
        System.out.println("测试前");
    }

    @After
    public void testAfter(){
        System.out.println("测试后");
    }

    @Test
    public void contextLoads() {
        System.out.println("测试中1");
        // 断言
        TestCase.assertEquals(1,1);
    }

    @Test
    public void contextLoads1() {
        System.out.println("测试中2");
        TestCase.assertEquals(1,1);
    }

}
