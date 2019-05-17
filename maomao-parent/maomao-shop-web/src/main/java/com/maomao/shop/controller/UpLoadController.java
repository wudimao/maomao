package com.maomao.shop.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import util.FastDFSClient;

import entity.Result;

@RestController
public class UpLoadController {
	@Value("${FILE_SERVER_URL}")
	private String file_server_url;
	@RequestMapping("/upload")
	public Result upload(MultipartFile file){
		String originalfilename= file.getOriginalFilename();
		String extName =originalfilename.substring(originalfilename.lastIndexOf(".")+1);
		try {
			util.FastDFSClient client =new FastDFSClient("classpath:config/fdfs_client.conf");
			String fileId=client.uploadFile(file.getBytes(), extName);
			String url=file_server_url+fileId;//图片完整地址
			System.out.println(url);
			return new Result(true, url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new Result(false, "上传失败");
		} 
			
	}
}
