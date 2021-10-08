package com.greengiant.website.service;

import com.greengiant.website.WebsiteApplication;
import com.greengiant.website.pojo.model.Role;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AuthServiceTest {

    @Autowired
    private AuthService authService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

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

    @Test
    void testDelUser() {
        long userId = 100l;
        userService.delUser(userId);
        System.out.println("delUser success");
    }

    @Test
    void testEditRole() {
        Role role = new Role();
        role.setId(2l);
        role.setDescription("guest description");
        roleService.editRole(role);
    }

}