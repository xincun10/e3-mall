package com.cara.content.service.test;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cara.common.pojo.EasyUIDataGridResult;
import com.cara.mapper.TbContentMapper;
import com.cara.pojo.TbContent;
import com.cara.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class PageTest {

	@Test
	public void testPageHelper() {
		ApplicationContext context = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		PageHelper.startPage(0, 10);
		TbContentMapper mapper = (TbContentMapper) context.getBean("tbContentMapper");
		
		Long categoryId = 97l;
		// 创建结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		
		//创建查询条件对象
		TbContentExample example = new TbContentExample();
		//封装查询条件
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		//执行查询
		List<TbContent> contents = mapper.selectByExampleWithBLOBs(example);
		
		//使用查询结果获取分页信息对象
		PageInfo<TbContent> pageInfo = new PageInfo<>(contents);
		//设置分页结果到结果对象
		result.setRows(pageInfo.getList());
		result.setTotal(pageInfo.getTotal());
		System.out.println(result.getTotal());
	}

}
