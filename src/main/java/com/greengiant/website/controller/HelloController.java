package com.greengiant.website.controller;

import com.greengiant.website.dao.ShiroUserDao;
import com.greengiant.website.model.ShiroUser;
import com.greengiant.website.utils.DownUploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @SuppressWarnings("all")
    @Autowired
    private ShiroUserDao shiroUserDao;

    @RequestMapping("/world")
    public String sayHello() {
        return "Hello, world!";
    }

    @RequestMapping("/users")
    public String getUserList() {
        //todo
        List<ShiroUser> list = shiroUserDao.getShiroUserList();
        //todo 修改判断
        if (list != null && list.size() > 0) {
            return String.valueOf(list.size());
        }
        return "empty list";
    }

    @RequestMapping("/index")
    public String index()
    {
        return "index";
    }

    @RequestMapping("/fnf")
    public String fileNFound()
    {
        return "404";
    }

    @RequestMapping("/ws")
    public String ws()
    {
        return "ws";
    }

    @RequestMapping("/download")
    public ResponseEntity<byte[]> down(@RequestParam String fileName)
    {
        try {
            return DownUploadFile.download(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
