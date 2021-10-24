package com.greengiant.website.controller;

import com.greengiant.infrastructure.utils.FileUtil;
import com.greengiant.infrastructure.utils.ResultUtils;
import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.pojo.model.FileInfo;
import com.greengiant.website.service.FileConfigService;
import com.greengiant.website.service.FileInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
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

    // todo 1.我更倾向于使用ResponseEntity而不是HttpServletResponse：1抽象层次更高封装的更好；2.返回值类型更统一【只需要确认一下怎么支持带进度的下载】
    // todo 结合http面试题再看一下这方面 30 张图解 HTTP 常见的面试题：https://zhuanlan.zhihu.com/p/112010468
    // todo postman和JUnit测试文件上传下载【base64String】
    // todo 这个util类应该把http相关的内容分离，放入FileController中去
    //  也可以做成fileService或者storageService
    // todo 关于上传下载进度、多文件上传这2个点，至少要前端、后端分别看一次
    // todo 先把我的ng2-file-upload搞熟，再看webUploader之类的【文件上传、下载果然没有想的那么简单】【ngx-bootstrap有文件上传吗？】
    // todo CommonsMultipartResolver：https://blog.csdn.net/qq_37671722/article/details/90671879
    // todo 对文件下载进度的理解：1.只要知道文件大小，从前端、后端、webSocket都很好实现

    /**
     * 1.这个方法是完全可以抽出来复用的，在我自己的代码里面复用一下；
     * 2.再次比较ResponseEntity【最好看一下这个类的源码】
     * 3.如果直接使用this.http.Post(url, param);会出现http failure for parsing ... 错误，数据转换错误，要使用PostExport，PostExport规定了responseType: 'blob'
     * 把sw-http-client.service.ts搞过去
     *
     * MIME 类型中，application/xml 与 text/xml 的区别：https://blog.csdn.net/kikajack/article/details/79233017
     */

    //TODO 文件下载还有2个重要问题：1.大文件下载（文件太大(例如视频)这种方式能否支持？能否支持进度显示？）2.怎么被其他模块调用

    /**
     * 【跨域】默认只有七种 simple response headers （简单响应首部）可以暴露给外部：
     * https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Access-Control-Expose-Headers
     * https://www.jianshu.com/p/0c26c6b11f33
     */

    // multipart/form-data与application/octet-stream【这2者应该都能进行文件上传】：http://www.voidcn.com/article/p-trrasylq-xc.html
    // todo HTTP请求中几种常见的Content-Type类型：http://t.zoukankan.com/xidianzxm-p-14241657.html

    @Value("${file.upload-path}")
    private String fileBasePath;

    @Autowired
    private FileConfigService fileConfigService;

    @Autowired
    private FileInfoService fileInfoService;

    @PostMapping("/fileExists")
    public Boolean fileExists(@RequestParam(value = "md5File") String md5File) {
        return fileInfoService.isExists(md5File);
    }

    @PostMapping("/upload")
    public ResultBean handleFileUpload(@RequestParam("file") MultipartFile file,
                                        @RequestBody FileInfo fileInfo) {
        String fileName = StringUtils.cleanPath(Objects.requireNonNull(file.getOriginalFilename()));// todo 再看一遍RequestEntity，对比MultipartFile
        Path path = Paths.get(fileBasePath + fileName);
        // todo 0.支持多文件上传【要把前后端一起写好，肯定还需要反复尝试的，不要怕麻烦和进展慢，彻底搞清楚】
        // todo 1.如果文件过多，就得把文件信息放入其他查询更快的数据存储中
        // todo 2.可以使用云的对象存储
        // 判断是否上传过
        if (fileInfoService.isExists(fileInfo.getMd5())) {
//            return ResultUtils.success(fileDownloadUri);// todo 如果还要判断其他信息，则上面不用isExists方法，而是返回一个对象
        }

        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);

            // todo 这个函数是不是用的不多？fromCurrentContextPath
            String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                    .path("/files/download/")// todo 抽到配置里面去
                    .path(fileName)
                    .toUriString();
            // todo 新文件名用md5
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
     * 看一下“MultipartFile extends InputStreamSource”是不是零拷贝【公司代码用到的】
     * 零拷贝 最大2G文件 FileChannel.transferTo：https://www.cnblogs.com/star521/p/9253213.html
     spring boot+webuploader实现大文件分片上传实例：
     https://zhuanlan.zhihu.com/p/29526454
     https://cloud.tencent.com/developer/article/1541199
     https://blog.csdn.net/kfyty725/article/details/104848966
     https://www.cnblogs.com/songsu/p/12197762.html

     大文件分片上传的要点：
        1.用md5来检查是否上传过（效率）以及作为临时文件的目录名称；
        2.合并文件的时候要用NIO的高效方法
        3.todo 是否可以上传一个开一个线程合并一个？防止大文件最终合并时候的卡顿感

     通用文件上传设计【文件上传根据这个文档搞出来】：https://blog.csdn.net/minaki_/article/details/85163343
     分布式文件上传服务架构设计：https://blog.csdn.net/moxiaomomo/article/details/78588082
     */

    // todo 聊聊MultipartFile的transferTo方法：https://blog.csdn.net/lezeqe/article/details/108937647

    // todo 文件里面的很多方法是需要加缓存的，配合CustomRealm里面的缓存读取

    // todo 分片（考虑并发）
    // todo 如果不把这些写好，就是高估了自己CRUD以外的能力。上传下载进度

    // todo SpringBoot实现文件的上传和下载：https://www.jianshu.com/p/be1af489551c
    // todo SpringBoot 文件上传(带进度条)与下载【文件上传监听器ProgressListener】：https://www.cnblogs.com/ruhuanxingyun/p/10868243.html
    // todo 在浏览器中异步下载文件监听下载进度：https://www.cnblogs.com/kevinblandy/p/13669904.html

    // todo 上传单个和多个文件：https://www.devglan.com/spring-boot/spring-boot-file-upload-download
    // todo 下载：1.进度【下载完后消息中心提示】；2.打包zip 断点续传
    // todo 再看：https://spring.io/guides/gs/uploading-files/。
    //  从这个文章也看出，我的单元测试技能是不够的。把这个做了就能提升一截
    /**
    *
        断点续传的原理：https://blog.csdn.net/lu1024188315/article/details/51803471#comments
        断点续传原理【自己先思考一下】：
        https://blog.csdn.net/weixin_38055381/article/details/82753480
        https://www.cnblogs.com/luozhixiang/p/9306240.html
        http://www.woshipm.com/pd/891969.html
        https://www.cnblogs.com/wangzehuaw/p/5610851.html
        https://www.jianshu.com/p/012c8a4dc661
    * */

    // todo 在分片上传中，每个分配最好也计算md5，这样后端判重逻辑简单一些（不用保存哪个分配属于哪个文件的信息）

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
