package com.greengiant.website.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

public class FileUtil {

    private FileUtil() {}

    // todo 结合http面试题再看一下这方面 30 张图解 HTTP 常见的面试题：https://zhuanlan.zhihu.com/p/112010468

    // todo postman和JUnit测试文件上传下载

    // todo 这个util类应该把http相关的内容分离，放入FileController中去
    //  也可以做成fileService或者storageService

	//TODO 文件下载还有2个重要问题：1.大文件下载（文件太大(例如视频)这种方式能否支持？）2.怎么被其他模块调用
	public static ResponseEntity<byte[]> download(String fileFullName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        // todo attachment格式最终传的是什么？和angular协同调通
        headers.setContentDispositionFormData("attachment", getShortName(fileFullName));
        
        return new ResponseEntity<>(toByteArray(fileFullName), headers, HttpStatus.CREATED);
    }

    void download(HttpServletResponse response, String fileName) {
        try {
            File file = new File(fileName);
            response.setContentType("application/octet-stream;charset-ISO8859-1");
            response.setContentType("multipart/form-data");
            response.setHeader("Content-Disposition", "attachment;filename=" + fileName);
            response.addHeader("Param", "no-cache");
            response.addHeader("Cache-Control", "no-cache");
            response.addHeader("Access-Control-Expose-Headers", "filename"); // 设置自定义header
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
