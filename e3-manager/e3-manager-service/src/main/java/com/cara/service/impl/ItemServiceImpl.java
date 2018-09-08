package com.cara.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cara.common.pojo.EasyUIDataGridResult;
import com.cara.mapper.TbItemMapper;
import com.cara.pojo.TbItem;
import com.cara.pojo.TbItemExample;
import com.cara.service.ItemService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

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
//		TbItemExample example = new TbItemExample();
//		Criteria criteria = example.createCriteria();
//		//查询条件
//		criteria.andIdEqualTo(itemId);
//		//执行查询
//		List<TbItem> list = itemMapper.selectByExample(example);
//		if(list!=null && list.size()>0)
//		{
//			return list.get(0);
//		}
//		return null;
		TbItem tbItem = itemMapper.selectByPrimaryKey(itemId);
		return tbItem;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		//设置分页信息
		PageHelper.startPage(page, rows);
		//执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		//创建返回值对象
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setRows(list);
		//取分页信息
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		result.setTotal(pageInfo.getTotal());		
		return result;
	}

}
