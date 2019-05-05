package com.cara.test.solrj;

import org.apache.solr.client.solrj.impl.CloudSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrCloud {

	@Test
	public void testAddDocument() throws Exception
	{
		//创建一个集群的连接，应该使用CloudSolrServer创建
		//zkHost:zookeeper的地址列表
		String zkHost = "192.168.42.130:2281,192.168.42.130:2282,192.168.42.130:2283";
		CloudSolrServer solrServer = new CloudSolrServer(zkHost);
		//设置一个defaultCollection属性
		solrServer.setDefaultCollection("collection2");
		//创建一个文档对象
		SolrInputDocument document = new SolrInputDocument();
		//向文档中添加域
		document.setField("id", "solrcloud01");
		document.setField("item_title", "测试商品01");
		document.setField("item_price", 100);
		//把文件写入索引库
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
}
