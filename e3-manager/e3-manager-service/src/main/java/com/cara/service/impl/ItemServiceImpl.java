package com.cara.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cara.mapper.TbItemMapper;
import com.cara.pojo.TbItem;
import com.cara.pojo.TbItemExample;
import com.cara.pojo.TbItemExample.Criteria;
import com.cara.service.ItemService;

/**
 * 商品管理Service
 * @author carazheng
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Autowired
	private TbItemMapper itemMapper;

	@Override
	public TbItem getItemById(long itemId) {
		TbItemExample example = new TbItemExample();
		Criteria criteria = example.createCriteria();
		//查询条件
		criteria.andIdEqualTo(itemId);
		//执行查询
		List<TbItem> list = itemMapper.selectByExample(example);
		if(list!=null && list.size()>0)
		{
			return list.get(0);
		}
		return null;
	}

}
