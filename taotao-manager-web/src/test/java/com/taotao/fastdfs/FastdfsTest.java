package com.taotao.fastdfs;

import java.io.IOException;

import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.taotao.common.utils.FastDFSClient;

//import org.csource.fastdfs.ClientGlobal;

public class FastdfsTest {

	public void testUpload() {
		// 1、将FastDFS提供的jar包导入到工程中，maven中央仓库没有，需要自己导入
		// 2、初始化全局配置，加载一个配置文件.
		try {
			ClientGlobal.init(
					"E:\\eclipse_neon_workspcae\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\properties\\client.conf");

			// 3、创建一个TrackerClient对象
			TrackerClient trackerClient = new TrackerClient();
			// 4、创建一个TrackerServer对象
			TrackerServer trackerServer = trackerClient.getConnection();
			// 5、申明一个StorageServer对象,null
			StorageServer storageServer = null;
			// 6、获得StorageClient对象
			StorageClient storageClient = new StorageClient(trackerServer, storageServer);
			// 7、直接调用StorageClient对象上传文件即可
			String[] strings=storageClient.upload_file("E:\\个人资料\\个人资料\\图片与音乐\\动漫图片收集\\1.jpg", "jpg", null);
			for(String str:strings){
				System.out.println(str);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MyException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	@Test
	public void testFastDFSUtil() throws IOException, MyException{
		FastDFSClient client=new FastDFSClient("E:\\eclipse_neon_workspcae\\taotao-manager\\taotao-manager-web\\src\\main\\resources\\properties\\client.conf");
		String[] strings=client.uploadFile("E:\\个人资料\\个人资料\\图片与音乐\\动漫图片收集\\2.jpg","jpg");
		for(String str:strings){
			System.out.println(str);
		}
	}

}
