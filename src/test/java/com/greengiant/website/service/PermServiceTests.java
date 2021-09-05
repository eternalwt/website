package com.greengiant.website.service;

import com.greengiant.website.WebsiteApplication;
import com.greengiant.website.enums.EntityTypeEnum;
import com.greengiant.website.pojo.model.Perm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {WebsiteApplication.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PermServiceTests {

    @Autowired
    private PermService permService;

    @Test
    public void testAddBatch() {
//        permController.addBatch();
    }

    @Test
    public void testGetPermListByCondition() {
        // 获取 role为管理员 的角色的 菜单 权限
        Perm perm = new Perm();
        perm.setEntity(EntityTypeEnum.ROLE.getName());
        perm.setResource("MENU");

        List<Perm> permList = permService.getPermList(perm);
        if (permList != null) {
            System.out.println("permList size: " + permList.size());
        } else {
            System.out.println("Empty permList...");
        }
    }

}
