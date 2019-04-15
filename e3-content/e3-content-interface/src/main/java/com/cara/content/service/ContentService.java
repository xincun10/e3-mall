package com.cara.content.service;

import com.cara.common.pojo.E3Result;
import com.cara.common.pojo.EasyUIDataGridResult;
import com.cara.pojo.TbContent;

public interface ContentService {
	
	//获取该分类下的所有内容
	public EasyUIDataGridResult getContentList(Long categoryId, Integer page, Integer rows);
	//新增内容
	public E3Result add(TbContent content);
	//删除内容
	public E3Result delete(String[] ids);
	//便捷内容
	public E3Result edit(TbContent content);

}
