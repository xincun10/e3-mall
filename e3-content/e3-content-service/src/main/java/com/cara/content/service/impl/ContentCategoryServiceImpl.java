package com.cara.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cara.common.pojo.E3Result;
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

	@Override
	public E3Result save(Long parentId, String name) {
		TbContentCategory category = new TbContentCategory();
		//补全属性
		category.setName(name);
		category.setParentId(parentId);
		//状态默认为1-可用
		category.setStatus(1);
		//默认排序值为1
		category.setSortOrder(1);
		//新增时的isparent默认是false
		category.setIsParent(false);		
		Date date = new Date();
		category.setCreated(date);
		category.setUpdated(date);
		
		//执行插入操作,返回主键
		mapper.insert(category);
		
		//更新其其父节点信息
		TbContentCategory parent = new TbContentCategory();
		parent.setId(parentId);
		//设置父节点的isparent为true
		parent.setIsParent(true);
		//修改父节点信息
		mapper.updateByPrimaryKeySelective(parent);
		// 返回E3Result时，需要将节点的ID封装进去。页面要求的数据格式是data.data.id，
		//所以此处需要封装category到E3Result
		return E3Result.ok(category);
	}

}
