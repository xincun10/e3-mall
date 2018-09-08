package com.cara.pagehelper;

import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.cara.mapper.TbItemMapper;
import com.cara.pojo.TbItem;
import com.cara.pojo.TbItemExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

public class PageHelperTest {

	@Test
	public void testPageHelper() throws Exception{
		//初始化spring容器
		ApplicationContext app = 
				new ClassPathXmlApplicationContext("classpath:spring/applicationContext-dao.xml");
		//从容器中获得Mapper代理对象
		TbItemMapper itemMapper = (TbItemMapper) app.getBean("tbItemMapper");
		//执行sql语句之前设置分页信息，使用PageHelper的startPage方法
		PageHelper.startPage(1, 10);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//取分页信息，PageInfo:1.总记录数2.总页数3.当前页码
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		System.out.println(pageInfo.getTotal());
		System.out.println(pageInfo.getPages());
		System.out.println(list.size());
	}
}
