package com.greengiant.website.service;

import com.greengiant.website.WebsiteApplication;
import com.greengiant.website.pojo.dto.MenuTreeNode;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MenuServiceTest {

    @Autowired
    private MenuService menuService;

    @Test
    void selectByRole() {
    }

    @Test
    void selectByUserId() {
    }

    @Test
    void updateRole() {
    }

    @Test
    void getMenuTree() {
        List<MenuTreeNode> list = menuService.getMenuTree();
        System.out.println("getMenuTree");
    }

}