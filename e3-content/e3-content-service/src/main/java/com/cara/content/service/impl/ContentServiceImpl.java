package com.cara.content.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.cara.common.jedis.JedisClient;
import com.cara.common.pojo.E3Result;
import com.cara.common.pojo.EasyUIDataGridResult;
import com.cara.common.utils.JsonUtils;
import com.cara.content.service.ContentService;
import com.cara.mapper.TbContentMapper;
import com.cara.pojo.TbContent;
import com.cara.pojo.TbContentExample;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ContentServiceImpl implements ContentService{
	
	@Autowired
	private TbContentMapper mapper;
	
	@Autowired
	private JedisClient jedisClient;
	
	//取配置文件内容
	@Value("${CONTENT_LIST}")
	private String CONTENT_LIST;

	@Override
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows) {
		// 创建结果对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		//设置分页
		PageHelper.startPage(page, rows);
		
		//创建查询条件对象
		TbContentExample example = new TbContentExample();
		//封装查询条件
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		//执行查询
		List<TbContent> contents = mapper.selectByExampleWithBLOBs(example);
		result.setRows(contents);
		
		//使用查询结果获取分页信息对象
		PageInfo<TbContent> pageInfo = new PageInfo<>(contents);
		//设置分页结果到结果对象
		result.setTotal(pageInfo.getTotal());
		
		return result;
	}

	@Override
	public E3Result add(TbContent content) {
		//补全数据
		content.setCreated(new Date());
		content.setUpdated(new Date());
		mapper.insertSelective(content);
		//缓存同步，删除缓存中对应的数据
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		return E3Result.ok();
	}

	/**
	 * 删除操作，支持批量删除
	 */
	@Override
	public E3Result delete(String[] ids) {
		try {
			for(String id:ids) {
				mapper.deleteByPrimaryKey(Long.valueOf(id));
				//缓存同步，删除缓存中对应的数据
				jedisClient.hdel(CONTENT_LIST, id.toString());
			}
			return E3Result.ok();
		} catch(Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	@Override
	public E3Result edit(TbContent content) {
		//修改更新时间
		content.setUpdated(new Date());
		mapper.updateByPrimaryKeyWithBLOBs(content);
		//缓存同步，删除缓存中对应的数据
		jedisClient.hdel(CONTENT_LIST, content.getCategoryId().toString());
		return E3Result.ok();
	}

	@Override
	public List<TbContent> queryContentList(Long categoryId) {
		//查询缓存，添加缓存不能影响正常业务逻辑，因此加上try..catch
		try {
			String json = jedisClient.hget(CONTENT_LIST, categoryId+"");
			if(StringUtils.isNotBlank(json))
			{
				List<TbContent> list = JsonUtils.jsonToList(json, TbContent.class);
				return list;
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
		//如果缓存中有直接响应结果
		//如果没有查询数据库
		//根据分类Id查询内容列表
		TbContentExample example = new TbContentExample();
		example.createCriteria().andCategoryIdEqualTo(categoryId);
		List<TbContent> list = mapper.selectByExample(example);
		
		//把查询结果添加到缓存
		try {
			jedisClient.hset(CONTENT_LIST, categoryId+"", JsonUtils.objectToJson(list));
		}catch(Exception e){
			e.printStackTrace();
		}
		return list;
	}

}
