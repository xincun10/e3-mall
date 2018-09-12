package com.cara.content.service;

import java.util.List;

import com.cara.common.pojo.E3Result;
import com.cara.common.pojo.EasyUITreeNode;

public interface ContentCategoryService {

	//获取分类列表
	public List<EasyUITreeNode> queryCategoryList(Long parentId);
	//添加内容分类
	public E3Result save(Long parentId, String name);
	//重命名分类
	public E3Result updateContentCategory(Long id, String name);
}
