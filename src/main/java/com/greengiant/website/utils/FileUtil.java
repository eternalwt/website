package com.greengiant.website.utils;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import java.io.*;

public class FileUtil {
	//TODO 文件下载还有2个重要问题：1.大文件下载（文件太大(例如视频)这种方式能否支持？）2.怎么被其他模块调用
	public static ResponseEntity<byte[]> download(String fileFullName) throws IOException {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
        headers.setContentDispositionFormData("attachment", getShortName(fileFullName));
        
        return new ResponseEntity<byte[]>(toByteArray(fileFullName), headers, HttpStatus.CREATED);
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
            int buf_size = 1024;  
            byte[] buffer = new byte[buf_size];  
            int len = 0;  
            while (-1 != (len = in.read(buffer, 0, buf_size))) {  
                bos.write(buffer, 0, len);  
            }  
            return bos.toByteArray();  
        } catch (IOException e) {  
            e.printStackTrace();  
            throw e;  
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