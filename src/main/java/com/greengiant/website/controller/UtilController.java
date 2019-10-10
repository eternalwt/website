package com.greengiant.website.controller;

import com.greengiant.website.dao.UserDao;
import com.greengiant.website.pojo.model.User;
import com.greengiant.website.utils.CaptchaUtil;
import com.greengiant.website.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Slf4j
@Deprecated
@RestController
@RequestMapping("/hello")
public class UtilController {

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
        } catch (IOException ex) {
            log.error("download failed, filename: [{}]", fileName, ex);
        }

        return null;
    }

    @RequestMapping("/captchaCode")
    public void getCode(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
        CaptchaUtil.getCode(req, resp);
    }

}
