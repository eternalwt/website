package com.greengiant.website.controller;

import com.greengiant.website.WebsiteApplication;
import com.greengiant.website.pojo.model.Perm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PermControllerTest {

    @Autowired
    private PermController permController;

    @Test
    public void testAddBatch() {

        permController.addBatch();

    }

}
