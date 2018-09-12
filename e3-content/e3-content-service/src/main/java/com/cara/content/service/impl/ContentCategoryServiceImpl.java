package com.cara.content.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cara.common.pojo.EasyUITreeNode;
import com.cara.content.service.ContentCategoryService;
import com.cara.mapper.TbContentCategoryMapper;
import com.cara.pojo.TbContentCategory;
import com.cara.pojo.TbContentCategoryExample;
import com.cara.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper mapper;
	
	@Override
	public List<EasyUITreeNode> queryCategoryList(Long parentId) {
		//根据父分类id，查询内容分类列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询，返回结果
		List<TbContentCategory> list = mapper.selectByExample(example);
		List<EasyUITreeNode> result = new ArrayList<>();
		for(TbContentCategory category : list)
		{
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(category.getId());
			node.setText(category.getName());
			node.setState(category.getIsParent()?"closed":"open");
			result.add(node);
		}
		return result;
	}

}
