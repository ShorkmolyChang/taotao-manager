package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Autowired
	private TbContentMapper contentMapper;

	@Override
	public List<EasyUITreeNode> getContentCategoryList(Long parentId) {
		// TODO Auto-generated method stub

		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		for (TbContentCategory contentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(contentCategory.getId());
			node.setText(contentCategory.getName());
			node.setState(contentCategory.getIsParent() ? "closed" : "open");
			result.add(node);
		}

		return result;
	}

	@Override
	public TaotaoResult addCategoryNode(Long parentId, String name) {
		// TODO Auto-generated method stub

		TbContentCategory category = new TbContentCategory();
		category.setIsParent(false);
		category.setName(name);
		category.setParentId(parentId);
		Date date = new Date();
		category.setCreated(date);
		category.setUpdated(date);
		// 1:正常，2:删除
		category.setStatus(1);
		// 排列序号，表示同级类目的展现次序，如果数值相等则按照名称次序排列，取值范围：>=0
		category.setSortOrder(0);

		// 插入数据
		contentCategoryMapper.insert(category);

		// 需要更新父节点的属性（当父节点没有子节点时，添加一个子节点，父节点的isParent为true）
		TbContentCategory category2 = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!category2.getIsParent()) {
			category2.setIsParent(true);
			contentCategoryMapper.updateByPrimaryKey(category2);
		}

		// mybatis有自增主键返回的功能,在事务中返回最后一次插入节点的id
		Long id = category.getId();

		return TaotaoResult.ok(id);
	}

	@Override
	public TaotaoResult addContent(TbContent content) {
		// TODO Auto-generated method stub

		content.setCreated(new Date());
		content.setUpdated(new Date());

		contentMapper.insert(content);

		return TaotaoResult.ok();
	}

	@Override
	public EasyUIDataGridResult getContentList(int page, int rows) {
		// TODO Auto-generated method stub
		PageHelper.startPage(page, rows);
		TbContentExample example = new TbContentExample();
		List<TbContent> list = contentMapper.selectByExample(example);
		// 分页处理
		PageInfo<TbContent> pageInfo = new PageInfo<TbContent>(list);
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		return result;
	}

}
