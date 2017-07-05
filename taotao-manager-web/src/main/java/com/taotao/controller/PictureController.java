package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.JsonUtils;
import com.taotao.service.PictureService;

@Controller
public class PictureController {
	
//	不能在controller中注入得到properties中设置的对象，因为扫描properties文件的设置在spring容器中，而controller对象扫描在springmvc中，
//	子容器中不能注入父容器的对象
//	@Value("${IMAGE_SERVER_BASE_RUL}")
//	private String image_server_url;

//	自动注入一个pictureService对象
	@Autowired
	private PictureService pictureService;

	// 参数名uploadFile有要求，与jquery中kinderEdit的参数要求一致
	@RequestMapping("/pic/upload")
//	直接调用response的write方法，将结果输出给客户端，必须为json字符串，不能为自定义对象。
	@ResponseBody
	public String uploadPic(MultipartFile uploadFile) {
		PictureResult result = pictureService.uploadPic(uploadFile);
		// 要求返回一个json字符串，浏览器兼容性最好
		return JsonUtils.objectToJson(result);
	}

}
