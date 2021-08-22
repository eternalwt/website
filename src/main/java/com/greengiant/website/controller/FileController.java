package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${file.upload-path}")
    private String fileBasePath;

    @PostMapping("/upload")
    public ResultBean handleFileUpload(@RequestParam("file") MultipartFile file) {// todo 为啥不是RequestBody？从最基础的部分思考起
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());// todo 根据提示重构一下【自己先思考】
        Path path = Paths.get(fileBasePath + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // todo 存入数据库（添加表）
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/download/")
                    .path(fileName)
                    .toUriString();

            return ResultUtils.success(fileDownloadUri);
        } catch (IOException ex) {
            log.error("handleFileUpload failed...", ex);
            return ResultUtils.fail();
        }
    }

    // todo 上传单个和多个文件：https://www.devglan.com/spring-boot/spring-boot-file-upload-download
    // todo 下载：1.进度【下载完后消息中心提示】；2.打包zip
    // todo 再看：https://spring.io/guides/gs/uploading-files/。
    //  从这个文章也看出，我的单元测试技能是不够的。把这个做了就能提升一截

    

}
