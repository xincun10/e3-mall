package com.cara.content.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cara.common.pojo.E3Result;
import com.cara.common.pojo.EasyUIDataGridResult;
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
		return E3Result.ok();
	}

}
