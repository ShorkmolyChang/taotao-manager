package com.taotao.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentCategoryService {

	public List<EasyUITreeNode> getContentCategoryList(Long parentId);
	
	public TaotaoResult addCategoryNode(Long parentId,String name);
	
	public TaotaoResult addContent(TbContent content);
	
	public EasyUIDataGridResult getContentList(int page,int rows);
	
}
