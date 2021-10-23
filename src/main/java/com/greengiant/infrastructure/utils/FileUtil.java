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

	public static ResponseEntity<byte[]> download(String fileFullName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        // 如果是文件导出，Content-Type 设置为 multipart/form-data，并且添加一个Content-Disposition设置为attachment;fileName=文件.后缀：https://www.jianshu.com/p/de5845b4c095
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", getShortName(fileFullName));
        
        return new ResponseEntity<>(toByteArray(fileFullName), headers, HttpStatus.CREATED);
    }

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
