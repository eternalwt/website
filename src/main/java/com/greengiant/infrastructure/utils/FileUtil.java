package com.greengiant.infrastructure.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileUtil {

    private FileUtil() {}

    // todo 1.我更倾向于使用ResponseEntity而不是HttpServletResponse：1抽象层次更高封装的更好；2.返回值类型更统一【只需要确认一下怎么支持带进度的下载】
    // todo 结合http面试题再看一下这方面 30 张图解 HTTP 常见的面试题：https://zhuanlan.zhihu.com/p/112010468
    // todo postman和JUnit测试文件上传下载
    // todo 这个util类应该把http相关的内容分离，放入FileController中去
    //  也可以做成fileService或者storageService

    /*1.这个方法是完全可以抽出来复用的，在我自己的代码里面复用一下；
    2.再次比较ResponseEntity【最好看一下这个类的源码】
    4.如果直接使用this.http.Post(url, param);会出现http failure for parsing ... 错误，数据转换错误，要使用PostExport，PostExport规定了responseType: 'blob'
    把sw-http-client.service.ts搞过去*/


    //TODO 文件下载还有2个重要问题：1.大文件下载（文件太大(例如视频)这种方式能否支持？能否支持进度显示？）2.怎么被其他模块调用
	public static ResponseEntity<byte[]> download(String fileFullName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        // 如果是文件导出，Content-Type 设置为 multipart/form-data，并且添加一个Content-Disposition设置为attachment;fileName=文件.后缀：https://www.jianshu.com/p/de5845b4c095
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", getShortName(fileFullName));
        
        return new ResponseEntity<>(toByteArray(fileFullName), headers, HttpStatus.CREATED);
    }

    /**
     * 【跨域】默认只有七种 simple response headers （简单响应首部）可以暴露给外部：
     * https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Headers/Access-Control-Expose-Headers
     * https://www.jianshu.com/p/0c26c6b11f33
      */

    // multipart/form-data与application/octet-stream【这2者应该都能进行文件上传】：http://www.voidcn.com/article/p-trrasylq-xc.html
    // todo HTTP请求中几种常见的Content-Type类型：http://t.zoukankan.com/xidianzxm-p-14241657.html
    void download(HttpServletResponse response, String fileName) {
        try {
            File file = new File(fileName);
//            response.setCharacterEncoding();// todo 默认的是啥？看看response代码
            response.setContentType(MediaType.MULTIPART_FORM_DATA_VALUE);// todo 这里使用的是枚举，spring-web对http的封装很多我们都没用起来，再看其他类和属性
            response.setHeader(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + fileName);
            response.addHeader("Param", "no-cache");
            response.addHeader(HttpHeaders.CACHE_CONTROL, "no-cache");
            response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, "filename"); // 设置可以暴露给外部的header
            response.addHeader("filename", URLEncoder.encode(fileName, "utf-8"));
            OutputStream os = response.getOutputStream();
            FileInputStream input = new FileInputStream(file);
            int b = 0;
            byte[] buffer = new byte[512];
            while (b != -1) {
                b = input.read(buffer);
                os.write(buffer, 0, b);
            }
            input.close();
            os.flush();
            os.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

	private static String getShortName(String fileFullName)
	{
		return fileFullName.split("\\")[fileFullName.split("\\").length - 1];
	}
	
	private static byte[] toByteArray(String filename) throws IOException {  
		  
        File f = new File(filename);
        if (!f.exists()) {
            throw new FileNotFoundException(filename);  
        }
  
        ByteArrayOutputStream bos = new ByteArrayOutputStream((int) f.length());  
        BufferedInputStream in = null;
        try {
            in = new BufferedInputStream(new FileInputStream(f));  
            int bufSize = 1024;
            byte[] buffer = new byte[bufSize];
            int len = 0;
            while (-1 != (len = in.read(buffer, 0, bufSize))) {
                bos.write(buffer, 0, len);
            }
            return bos.toByteArray();  
        } catch (IOException ex) {
            ex.printStackTrace();
            throw ex;
        } finally {
            try {
                in.close();  
            } catch (IOException e) {  
                e.printStackTrace();
            }
            bos.close();  
        }
    }

}
