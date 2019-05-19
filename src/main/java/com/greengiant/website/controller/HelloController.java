package com.greengiant.website.controller;

import com.greengiant.website.dao.UserDao;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.utils.FileUtil;
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

    @Autowired
    private UserDao userDao;

    @RequestMapping("/world")
    public String sayHello() {
        return "Hello, world!";
    }

    @RequestMapping("/users")
    public String getUserList() {
        //todo
        List<User> list = userDao.selectAll();
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
            return FileUtil.download(fileName);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return null;
    }

}
