package com.cara.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cara.common.pojo.E3Result;
import com.cara.common.pojo.EasyUITreeNode;
import com.cara.content.service.ContentCategoryService;

@Controller
@RequestMapping("/contentCategory")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService service;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EasyUITreeNode> list(
			@RequestParam(value="id", defaultValue="0")Long parentId)
	{
		return service.queryCategoryList(parentId);
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public E3Result create(Long parentId, String name)
	{
		return service.save(parentId, name);
	}
}
