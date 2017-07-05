package com.taotao.service.impl;

import java.io.IOException;

import org.csource.common.MyException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.taotao.common.pojo.PictureResult;
import com.taotao.common.utils.FastDFSClient;
import com.taotao.service.PictureService;

@Service
public class PictureServiceImpl implements PictureService {

	// 从properties配置文件中读取图片服务器的url并赋值给image_server_url。
	@Value("${IMAGE_SERVER_BASE_RUL}")
	private String image_server_url;

	@Override
	public PictureResult uploadPic(MultipartFile picFile) {
		// TODO Auto-generated method stub
		PictureResult result = new PictureResult();
		if (picFile.isEmpty()) {
			result.setError(1);
			result.setMessage("图片为空");
			return result;
		}
		try {
			// 取图片扩展名
			String originalName = picFile.getOriginalFilename();
			String extName = originalName.substring(originalName.lastIndexOf(".") + 1);
			// 图片上传
			FastDFSClient client = new FastDFSClient("classpath:properties/client.conf");
			String[] url = client.uploadFile(picFile.getBytes(), extName);
			// 设置返回值，确定服务器ip地址
			result.setError(0);
			result.setUrl(image_server_url + url[0] + "/" + url[1]);
		} catch (IOException | MyException e) {
			// TODO Auto-generated catch block
			// 抛出异常，上传失败
			e.printStackTrace();
			result.setError(1);
			result.setMessage("上传失败！");
		}
		return result;
	}

}
