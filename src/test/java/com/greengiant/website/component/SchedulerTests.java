package com.greengiant.website.component;

import com.greengiant.website.WebsiteApplication;
import com.greengiant.website.scheduler.CustomSchedulingConfigurer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class SchedulerTests {

    @Before
    public void testBefore(){
        System.out.println("测试前");
    }

    @Test
    public void test001() {
        int minute = 1;
        // 这样测试不了，因为函数跑完环境就不存在了，加断点也不行，思考一下
        CustomSchedulingConfigurer.setIntervalMinute(minute);
        System.out.println("Scheduler interval minute changed to " + minute);
        System.out.println("waiting...");
    }

}
