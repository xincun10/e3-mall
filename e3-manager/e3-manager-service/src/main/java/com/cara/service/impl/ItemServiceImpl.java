package com.cara.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cara.common.pojo.E3Result;
import com.cara.common.pojo.EasyUIDataGridResult;
import com.cara.common.utils.IDUtils;
import com.cara.mapper.TbItemDescMapper;
import com.cara.mapper.TbItemMapper;
import com.cara.pojo.TbItem;
import com.cara.pojo.TbItemDesc;
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
	@Autowired
	private TbItemDescMapper itemDescMapper;

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

	@Override
	public E3Result addItem(TbItem item, String desc) {
		//生成商品id
		long itemId = IDUtils.genItemId();
		//设置商品id
		item.setId(itemId);
		//设置上下架状态1-正常，2-下架，3-删除
		item.setStatus((byte)1);
		//设置商品日期
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		//添加商品信息
		itemMapper.insert(item);
		//创建一个商品描述表对应的pojo对象
		TbItemDesc itemDesc = new TbItemDesc();
		//补全属性 
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		//向商品描述表中插入数据
		itemDescMapper.insert(itemDesc);
		//返回结果对象
		return E3Result.ok();
	}

}
