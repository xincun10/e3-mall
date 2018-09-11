package com.cara.fast;

import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.csource.fastdfs.StorageServer;
import org.csource.fastdfs.TrackerClient;
import org.csource.fastdfs.TrackerServer;
import org.junit.Test;

import com.cara.common.utils.FastDFSClient;

public class FastDfsTest {
	// 客户端配置文件
	private String conf_filename = "/conf/fdfs_client.conf";
	// 本地文件，要上传的文件
	private String filename = "I:\\pictures\\老田\\hebe.gif";

	@Test
	public void testUpload() throws Exception
	{
		//创建一个配置文件，文件名任意，内容是tracker服务器的地址
		//指定配置文件的完整路径
		String url = this.getClass().getResource(conf_filename).getPath();
		//使用全局对象加载配置文件
		ClientGlobal.init(url);
		//创建一个TrackClient对象
		TrackerClient trackerClient = new TrackerClient();
		//通过TrackerClient获得一个TrackerServer对象
		TrackerServer trackerServer = trackerClient.getConnection();
		//创建一个StorageServer的引用，可以是null
		StorageServer storageServer = null;
		//创建一个StorageClient，参数需要TrackerServer和StorageServer
		StorageClient storageClient = new StorageClient(trackerServer, storageServer);
		//使用StorageClient上传文件
		String[] strings = storageClient.upload_file(filename, "gif", null);
		
		for(String string: strings)
		{
			System.out.println(string);
		}
	}
	
	//测试工具类
	@Test
	public void testFastDfsClient() throws Exception
	{
//		String url = this.getClass().getResource(conf_filename).getPath();
//		FastDFSClient fastDFSClient = new FastDFSClient(url);
		FastDFSClient fastDFSClient = new FastDFSClient("classpath:conf/fdfs_client.conf");
		String string = fastDFSClient.uploadFile(filename, "gif");
		System.out.println(string);
	}
}
