package com.greengiant.website.controller;

import java.io.IOException;

import com.greengiant.website.utils.DownUploadFile;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class Hello {
	
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
			return DownUploadFile.download(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
}
