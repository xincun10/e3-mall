package com.cara.portal.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.cara.common.pojo.AdResult;
import com.cara.common.utils.JsonUtils;
import com.cara.content.service.ContentService;
import com.cara.pojo.TbContent;

@Controller
public class IndexController {
	
	@Value("${AD1_CATEGORY_ID}")
	private Long categoryId;
	@Value("${AD1_WIDTH}")
	private int width;
	@Value("${AD1_WIDTHB}")
	private int widthB;
	@Value("${AD1_HEIGHT}")
	private int heigth;
	@Value("${AD1_HEIGHTB}")
	private int heigthB;

	@Autowired
	private ContentService service;

	@RequestMapping("/index")
	public String index(Model model)
	{
		List<TbContent> contentList = service.queryContentList(categoryId);
		List<AdResult> adList = new ArrayList<>();
		AdResult ad;
		
		for(TbContent tbContent: contentList)
		{
			ad = new AdResult();
			ad.setAlt(tbContent.getTitle());
			ad.setHref(tbContent.getUrl());
			ad.setSrc(tbContent.getPic());
			ad.setSrcB(tbContent.getPic2());
			//设置图片的宽和高
			ad.setWidth(width);
			ad.setWidthB(widthB);
			ad.setHeight(heigth);
			ad.setHeightB(heigthB);
			
			adList.add(ad);
		}
		//将广告集合转为json数组
		String adJsonArr = JsonUtils.objectToJson(adList);
		//将json数组放入request域
		model.addAttribute("ad1", adJsonArr);
		
		return "index";
	}
}
