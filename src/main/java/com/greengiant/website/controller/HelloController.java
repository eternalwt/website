package com.greengiant.website.controller;

import com.greengiant.website.dao.user.UserDao;
import com.greengiant.website.utils.DownUploadFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("/hello")
public class HelloController {

    @SuppressWarnings("all")
    @Autowired
    private UserDao userDao;

    @RequestMapping("/world")
    public String sayHello() {
        return "Hello, world!";
    }

    public String getUserList() {
        //todo

        return null;
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
