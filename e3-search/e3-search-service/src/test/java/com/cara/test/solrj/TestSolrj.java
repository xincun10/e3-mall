package com.cara.test.solrj;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.junit.Test;

public class TestSolrj {

	//添加文档
	@Test
	public void addDocument() throws Exception
	{
		//创建SolrServer对象
		String baseURL = "http://192.168.42.128:8080/solr/collection1";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		//创建一个文档对象SolrInputDocument
		SolrInputDocument document = new SolrInputDocument();
		//向文档对象中添加域，文档中必须包含一个id域，所有的域的名称必须在schema.xml中定义
		document.addField("id", "doc1");
		document.addField("item_title", "测试商品01");
		document.addField("item_price", 1000);
		//把文档写入索引域
		solrServer.add(document);
		//提交
		solrServer.commit();
	}
	
	//删除文档
	@Test
	public void deleteDocument() throws Exception
	{
		//创建SolrServer对象
		String baseURL = "http://192.168.42.128:8080/solr/collection1";
		SolrServer solrServer = new HttpSolrServer(baseURL);
		//删除文档
//		solrServer.deleteById("doc1");//两种方法都可以
		solrServer.deleteByQuery("id:doc1");
		//提交
		solrServer.commit();
	}
}
