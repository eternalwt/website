package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.FileInfo;
import com.greengiant.website.service.FileConfigService;
import com.greengiant.website.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;

@Slf4j
@RestController
@RequestMapping("/file")
public class FileController {

    @Value("${file.upload-path}")
    private String fileBasePath;

    @Autowired
    private FileConfigService fileConfigService;

    @Autowired
    private FileInfoService fileInfoService;

    @PostMapping("/upload")
    public ResultBean handleFileUpload(@RequestParam("file") MultipartFile file,
                                        @RequestBody FileInfo fileInfo) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path path = Paths.get(fileBasePath + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // todo 存入数据库（添加表）
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/download/")// todo 抽到配置里面去
                    .path(fileName)
                    .toUriString();

            return ResultUtils.success(fileDownloadUri);
        } catch (IOException ex) {
            log.error("handleFileUpload failed...", ex);
            return ResultUtils.fail();
        }
    }

    // todo SpringBoot实现文件的上传和下载：https://www.jianshu.com/p/be1af489551c
    // todo SpringBoot 文件上传(带进度条)与下载：https://www.cnblogs.com/ruhuanxingyun/p/10868243.html
    // todo 在浏览器中异步下载文件监听下载进度：https://www.cnblogs.com/kevinblandy/p/13669904.html

    // todo 上传单个和多个文件：https://www.devglan.com/spring-boot/spring-boot-file-upload-download
    // todo 下载：1.进度【下载完后消息中心提示】；2.打包zip 断点续传
    // todo 再看：https://spring.io/guides/gs/uploading-files/。
    //  从这个文章也看出，我的单元测试技能是不够的。把这个做了就能提升一截
    /**
    *
        断点续传的原理（自己先想想怎么写）：https://blog.csdn.net/lu1024188315/article/details/51803471#comments
        断点续传原理【自己先思考一下】：
        https://blog.csdn.net/weixin_38055381/article/details/82753480
        https://www.cnblogs.com/luozhixiang/p/9306240.html
        http://www.woshipm.com/pd/891969.html
        https://www.cnblogs.com/wangzehuaw/p/5610851.html
        https://www.jianshu.com/p/012c8a4dc661
    * */
    

}
