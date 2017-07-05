package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbContent;
import com.taotao.service.ContentCategoryService;

@Controller
public class ContentController {

	@Autowired
	private ContentCategoryService contentCategoryService;
	@Value("${REDIS_BASE_URL}")
	private String REDIS_BASE_URL;
	@Value("${REDIS_CONTENT_SYNC_URL}")
	private String REDIS_CONTENT_SYNC_URL;

	@RequestMapping("/content/category/list")
	@ResponseBody
	public List<EasyUITreeNode> getCategoryNodeByParentId(
			@RequestParam(value = "id", defaultValue = "0") Long parentId) {
		List<EasyUITreeNode> result = contentCategoryService.getContentCategoryList(parentId);
		return result;
	}
	
	@RequestMapping("/content/category/create")
	@ResponseBody
	public  TaotaoResult addCategoryNode(Long parentId,String name){
		TaotaoResult result=contentCategoryService.addCategoryNode(parentId, name);
		return result;
	}
	
	@RequestMapping("/content/category/update")
	@ResponseBody
	public void updateCategoryNode(Long id,String name){
		
	}
	
//	注意1：delete需要注意该节点的父节点是否还有孩子节点，否则需要更新父节点的isParent属性
//	注意2：如果该节点是一个父节点，则需要递归的删除其子节点
	@RequestMapping("/content/category/delete/")
	@ResponseBody
	public void deleteCategoryNode(Long parentId,Long id){
		
	}
	
	
//	分页获取全部的内容列表
	@RequestMapping("/content/query/list")
	@ResponseBody
	public EasyUIDataGridResult getContentList(Integer page,Integer rows){
		EasyUIDataGridResult result=contentCategoryService.getContentList(page, rows);
		return result;
	}
	
	
	@RequestMapping("/content/save")
	@ResponseBody
	public TaotaoResult addContent(TbContent content){
//		添加一条content
		TaotaoResult result=contentCategoryService.addContent(content);
//		调用taotao-rest发布的服务，删除content对应的cid在redis中的所有数据
		HttpClientUtil.doGet(REDIS_BASE_URL+REDIS_CONTENT_SYNC_URL+content.getCategoryId());
		return result;
	}

}
