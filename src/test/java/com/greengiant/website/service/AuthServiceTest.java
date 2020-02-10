package com.greengiant.website.service;

import com.greengiant.website.WebsiteApplication;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
//
//Error creating bean with name 'serverEndpointExporter' 问题的解决：
//        https://blog.csdn.net/qq_27101653/article/details/85072241

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void login() {
        authService.login("gao3", "123456", false);
    }

    @Test
    void jwtLogin() {
    }
}