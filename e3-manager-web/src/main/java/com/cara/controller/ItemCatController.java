package com.cara.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cara.common.pojo.EasyUITreeNode;
import com.cara.service.ItemCatService;

/**
 * 商品分类管理Controller
 * @author Administrator
 *
 */
@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping("/itemCat/list")
	@ResponseBody
	public List<EasyUITreeNode> getItemCatList(
			@RequestParam(value="id", defaultValue="0")Long parentId)
	{
		//调用服务查询节点列表
		List<EasyUITreeNode> itemCatList = itemCatService.getItemList(parentId);
		return itemCatList;
	}
}
