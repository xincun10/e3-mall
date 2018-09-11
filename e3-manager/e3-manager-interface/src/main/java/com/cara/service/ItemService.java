package com.cara.service;

import com.cara.common.pojo.E3Result;
import com.cara.common.pojo.EasyUIDataGridResult;
import com.cara.pojo.TbItem;

public interface ItemService {

	TbItem getItemById(long itemId);
	//获取分页查询结果
	EasyUIDataGridResult getItemList(int page, int rows);
	//添加商品
	E3Result addItem(TbItem item, String desc);
}
