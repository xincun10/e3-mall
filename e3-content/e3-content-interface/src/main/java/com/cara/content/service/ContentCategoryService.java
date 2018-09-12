package com.cara.content.service;

import java.util.List;

import com.cara.common.pojo.EasyUITreeNode;

public interface ContentCategoryService {

	//获取分类列表
	public List<EasyUITreeNode> queryCategoryList(Long parentId);
}
