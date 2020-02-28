package com.greengiant.website.controller;

import com.greengiant.website.pojo.ResultBean;
import com.greengiant.website.utils.ResultUtils;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Controller
public class FileController {



//    private final StorageService storageService;
// todo 再看：https://spring.io/guides/gs/uploading-files/。
//  从这个文章也看出，我的单元测试技能是不够的。把这个做了就能提升一截
//
//    @Autowired
//    public FileController(StorageService storageService) {
//        this.storageService = storageService;
//    }

//    @GetMapping("/")
//    public String listUploadedFiles(Model model) throws IOException {
//
//        model.addAttribute("files", storageService.loadAll().map(
//                path -> MvcUriComponentsBuilder.fromMethodName(FileUploadController.class,
//                        "serveFile", path.getFileName().toString()).build().toString())
//                .collect(Collectors.toList()));
//
//        return "uploadForm";
//    }

//    @GetMapping("/files/{filename:.+}")
//    @ResponseBody
//    public ResponseEntity<Resource> serveFile(@PathVariable String filename) {
//
//        Resource file = storageService.loadAsResource(filename);
//        return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION,
//                "attachment; filename=\"" + file.getFilename() + "\"").body(file);
//    }

    private static final String fileBasePath = "/";

    @PostMapping("/upload")
    public ResultBean handleFileUpload(@RequestParam("file") MultipartFile file) {// todo 为啥不是RequestBody？
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(fileBasePath + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // todo 路径放入配置文件
        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/files/download/")
                .path(fileName)
                .toUriString();

        return ResultUtils.success(fileDownloadUri);
    }

    // todo 上传单个和多个文件：https://www.devglan.com/spring-boot/spring-boot-file-upload-download
    //  这个文章里面还有下载多个文件同时打包的操作（要试一下，我们系统可能用得上）

//    @ExceptionHandler(StorageFileNotFoundException.class)
//    public ResponseEntity<?> handleStorageFileNotFound(StorageFileNotFoundException exc) {
//        return ResponseEntity.notFound().build();
//    }

}
