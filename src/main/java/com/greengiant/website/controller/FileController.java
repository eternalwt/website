package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.FileInfo;
import com.greengiant.website.service.FileConfigService;
import com.greengiant.website.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.DigestUtils;
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

    @PostMapping("/checkFile")
    public Boolean checkFile(@RequestParam(value = "md5File") String md5File) {
        Boolean exist = false;

        // todo
		/*if(true) {
			exist = true;
		}*/
        return exist;
    }

    @PostMapping("/upload")
    public ResultBean handleFileUpload(@RequestParam("file") MultipartFile file,
                                        @RequestBody FileInfo fileInfo) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));
        Path path = Paths.get(fileBasePath + fileName);

        // todo 1.如果文件过多，就得把文件信息放入其他查询更快的数据存储中
        // todo 2.可以使用云的对象存储
        // 判断是否上传过
        if (fileInfo.getMd5() != null) {// todo 并且存在。是否抽一个方法供分片和单独上传一起使用？
//            return ResultUtils.success(fileDownloadUri);
        }

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // todo 这个函数是不是用的不多？fromCurrentContextPath
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/download/")// todo 抽到配置里面去
                    .path(fileName)
                    .toUriString();
            // todo 新文件名
            // todo 肯定不能根据原文件名计算md5，太容易重复了
            DigestUtils.md5DigestAsHex(fileInfo.getOriginalName().getBytes());
            fileInfoService.save(fileInfo);
            // file.transferTo();// todo 据说这个方法是zero-copy，再看看

            return ResultUtils.success(fileDownloadUri);
        } catch (IOException ex) {
            log.error("handleFileUpload failed...", ex);
            return ResultUtils.fail();
        }
    }

    /**
     *
     spring boot+webuploader实现大文件分片上传实例：
     https://zhuanlan.zhihu.com/p/29526454
     https://cloud.tencent.com/developer/article/1541199
     https://blog.csdn.net/kfyty725/article/details/104848966
     https://www.cnblogs.com/songsu/p/12197762.html

     大文件分片上传的要点：
        1.用md5来检查是否上传过（效率）以及作为临时文件的目录名称；
        2.合并文件的时候要用NIO的高效方法
        3.todo 是否可以上传一个开一个线程合并一个？防止大文件最终合并时候的卡顿感
     */

    // todo 分片
    // todo 如果不把这些写好，就是高估了自己CRUD以外的能力。上传下载进度
    // todo 分片上传

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

    // todo 在分片上传中，每个分配最好也计算md5，这样后端判重逻辑简单一些（不用保存哪个分配属于哪个文件的信息）


}
