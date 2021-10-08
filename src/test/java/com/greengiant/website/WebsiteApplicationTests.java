package com.greengiant.website;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class WebsiteApplicationTests {

    @BeforeEach
    public void testBefore(){
        System.out.println("测试前");
    }

    @AfterEach
    public void testAfter(){
        System.out.println("测试后");
    }

    @Test
    public void contextLoads() {
        System.out.println("测试中1");
        // 断言
        Assertions.assertEquals(1,1);
    }

    @Test
    public void contextLoads1() {
        System.out.println("测试中2");
        Assertions.assertEquals(1,1);
    }

    @Test
    public void testVoidStream() {
        List<Integer> list = new ArrayList<>();
        list.add(2);
        list.add(1);
        list.add(3);
        list.add(5);

        try {
            list.stream().filter(x -> x > 10).max((a, b) -> a > b ? 1 : -1).get();
        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

}
