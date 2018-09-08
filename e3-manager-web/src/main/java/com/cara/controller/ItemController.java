package com.cara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cara.common.pojo.EasyUIDataGridResult;
import com.cara.pojo.TbItem;
import com.cara.service.ItemService;

/**
 * 商品管理controller
 * @author carazheng
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService;
	
	@RequestMapping("/item/{itemId}")
	@ResponseBody//响应json对象，responsebody自动转换成json对象
	public TbItem TbItemById(@PathVariable Long itemId)
	{
		TbItem tbItem = itemService.getItemById(itemId);
		return tbItem;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows)
	{
		//调用服务查询商品列表
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}
	
}
