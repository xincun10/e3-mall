package com.cara.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cara.common.pojo.E3Result;
import com.cara.common.pojo.EasyUITreeNode;
import com.cara.content.service.ContentCategoryService;
import com.cara.mapper.TbContentCategoryMapper;
import com.cara.pojo.TbContentCategory;
import com.cara.pojo.TbContentCategoryExample;
import com.cara.pojo.TbContentCategoryExample.Criteria;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper mapper;
	
	@Override
	public List<EasyUITreeNode> queryCategoryList(Long parentId) {
		//根据父分类id，查询内容分类列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询，返回结果
		List<TbContentCategory> list = mapper.selectByExample(example);
		List<EasyUITreeNode> result = new ArrayList<>();
		for(TbContentCategory category : list)
		{
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(category.getId());
			node.setText(category.getName());
			node.setState(category.getIsParent()?"closed":"open");
			result.add(node);
		}
		return result;
	}

	@Override
	public E3Result save(Long parentId, String name) {
		TbContentCategory category = new TbContentCategory();
		//补全属性
		category.setName(name);
		category.setParentId(parentId);
		//状态默认为1-可用
		category.setStatus(1);
		//默认排序值为1
		category.setSortOrder(1);
		//新增时的isparent默认是false
		category.setIsParent(false);		
		Date date = new Date();
		category.setCreated(date);
		category.setUpdated(date);
		
		//执行插入操作,返回主键
		mapper.insert(category);
		
		//更新其其父节点信息
		TbContentCategory parent = new TbContentCategory();
		parent.setId(parentId);
		//设置父节点的isparent为true
		parent.setIsParent(true);
		//修改父节点信息
		mapper.updateByPrimaryKeySelective(parent);
		// 返回E3Result时，需要将节点的ID封装进去。页面要求的数据格式是data.data.id，
		//所以此处需要封装category到E3Result
		return E3Result.ok(category);
	}

	@Override
	public E3Result updateContentCategory(Long id, String name) {
		TbContentCategory category = new TbContentCategory();
		category.setId(id);
		category.setName(name);
		mapper.updateByPrimaryKeySelective(category);
		
		return E3Result.ok();
	}

	@Override
	public void deleteContentCategory(Long parentId, Long id) {
		//如果有子分类，则不允许删除。
		TbContentCategory category = mapper.selectByPrimaryKey(id);
		if((category!=null) && (!category.getIsParent()))
		{
			mapper.deleteByPrimaryKey(id);
			/*
	         * 查看删除节点的父节点还有没有其他子节点，如果没有子节点，将其设置为子节点
	         */
			TbContentCategory parent = mapper.selectByPrimaryKey(parentId);
			//判断是否为父节点
	        if(!this.haveChild(parent)){
	            //修改其isPrent属性为false
	            parent.setIsParent(false);
	            //保存修改
	            mapper.updateByPrimaryKeySelective(parent);
	        }
		}
		
	}
	
	/*
     * 判断内容分类是否为父节点（即是否含有子节点）
     */
    private boolean haveChild(TbContentCategory parent) {
        //封装查询条件
        TbContentCategoryExample example = new TbContentCategoryExample();
        example.createCriteria().andParentIdEqualTo(parent.getId());
        //执行查询
        List<TbContentCategory> categories = mapper.selectByExample(example);
        //判断是否为空且长度大于0，如果为空或不大于0，则表示不是父节点
        if(categories != null && categories.size() > 0){
            return true;
        }
        return false;
    }

}
