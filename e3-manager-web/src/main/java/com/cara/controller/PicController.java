package com.cara.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cara.common.utils.FastDFSClient;

@RequestMapping("/pic")
@Controller
public class PicController {
	//读取配置文件
	@Value("${IMAGE_SERVER_URL}")
	private String IMAGE_SERVER_URL;

	@RequestMapping("/upload")
	@ResponseBody
	public Map upload(MultipartFile uploadFile)
	{
		Map map = new HashMap<>();		
		try {
			//把图片上传到图片服务器
			FastDFSClient client = new FastDFSClient("classpath:conf/fdfs_client.conf");
			//获取上传文件的扩展名
			String originalFilename = uploadFile.getOriginalFilename();
			String extName = originalFilename.substring(originalFilename.lastIndexOf(".")+1);
			//通过工具类实现上传，返回上传文件的路径
			String path = client.uploadFile(uploadFile.getBytes(), extName);
			//补充为完整的url,加上服务器的ip和端口号
			String url = IMAGE_SERVER_URL + path;
			//将结果封装到map中返回
			map.put("error", 0);
			map.put("url", url);
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			//上传失败
			map.put("error", 1);
			map.put("message", "图片上传失败！");
			return map;
		}		
	}
}
