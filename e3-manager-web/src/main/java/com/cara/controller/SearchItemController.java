package com.cara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cara.common.pojo.E3Result;
import com.cara.search.service.SearchItemService;

/**
 * 导入商品数据到索引库
 * @author Administrator
 *
 */
@RequestMapping("/index/item")
@Controller
public class SearchItemController {

	@Autowired
	private SearchItemService searchItemService;
	
	@RequestMapping("/import")
	@ResponseBody
	public E3Result importIndex()
	{
		E3Result e3Result = searchItemService.importAllItems();
		return e3Result;
	}
}
