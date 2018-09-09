package com.cara.service;

import java.util.List;

import com.cara.common.pojo.EasyUITreeNode;

public interface ItemCatService {

	List<EasyUITreeNode> getItemList(long parentId);
}
