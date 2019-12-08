package com.greengiant.website.controller;

import com.greengiant.website.utils.CaptchaUtil;
import com.greengiant.website.utils.FileUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Deprecated
@RestController
@RequestMapping("/hello")
public class UtilController {

    @RequestMapping("/world")
    public String sayHello() {
        return "Hello, world!";
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

//    public void export(HttpServletResponse response) {
//        String filename = "aaa.xls";
//        HSSFWorkbook wb = new HSSFWorkbook();
//        HSSFSheet sheet = wb.createSheet("Sheet001");
//
//        response.setContentType("application/vnd.ms-excel");
//        response.addHeader("Content-Disposition", "attachment;filename=" + filename);
//        response.flushBuffer();
//
//        wb.write(response.getOutputStream());
//    }

}
