package com.cara.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cara.common.pojo.EasyUITreeNode;
import com.cara.mapper.TbItemCatMapper;
import com.cara.pojo.TbItemCat;
import com.cara.pojo.TbItemCatExample;
import com.cara.pojo.TbItemCatExample.Criteria;
import com.cara.service.ItemCatService;
/**
 * 商品分类管理
 * @author Administrator
 *
 */
@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	@Override
	public List<EasyUITreeNode> getItemList(long parentId) {
		//根据parentId查询子节点列表
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		//设置查询条件
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbItemCat> list = itemCatMapper.selectByExample(example);
		//创建返回结果list
		List<EasyUITreeNode> resultList = new ArrayList<>();
		//把列表转换成EasyUITreeNode列表
		for(TbItemCat tbItemCat:list)
		{
			EasyUITreeNode node = new EasyUITreeNode();
			//设置属性
			node.setId(tbItemCat.getId());
			node.setText(tbItemCat.getName());
			node.setState(tbItemCat.getIsParent()?"closed":"open");
			resultList.add(node);
		}
		//返回结果
		return resultList;
	}
	
}
