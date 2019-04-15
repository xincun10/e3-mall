package com.cara.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cara.common.pojo.E3Result;
import com.cara.common.pojo.EasyUIDataGridResult;
import com.cara.content.service.ContentService;
import com.cara.pojo.TbContent;

@Controller
@RequestMapping("/content")
public class ContentController {

	@Autowired
	private ContentService contentService;
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EasyUIDataGridResult list(Long categoryId, Integer page, Integer rows)
	{
		EasyUIDataGridResult res = contentService.getContentList(categoryId, page, rows);
		return res;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public E3Result save(TbContent content)
	{
		return contentService.add(content);
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public E3Result delete(String[] ids)
	{
		return contentService.delete(ids);
	}
	
	@RequestMapping("/edit")
	@ResponseBody
	public E3Result edit(TbContent content)
	{
		return contentService.edit(content);
	}
}
