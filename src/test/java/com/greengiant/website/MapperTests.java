package com.greengiant.website;

import com.greengiant.website.dao.MenuMapper;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class MapperTests {

    @Autowired
    private MenuMapper menuMapper;

    @Test
    void testUpdateRoleDel() {
        menuMapper.updateRoleDel("控制台", "1");
        System.out.println("finished...");
    }

}
