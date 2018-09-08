package com.cara.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面跳转controller
 * @author Administrator
 *
 */
@Controller
public class PageController {

	@RequestMapping("/")
	public String showIndex()
	{
		//返回视图
		return "index";
	}
	@RequestMapping("/system/{page}")
	public String showPage(@PathVariable String page)
	{
		//page的值和jsp页面的名称是一致的，比如：item-add
		return page;
	}
}
