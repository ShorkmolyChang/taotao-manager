package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemcatMapper;

	@Override
	public List<EasyUITreeNode> getItemCatList(long parentId) {
		// TODO Auto-generated method stub

		TbItemCatExample example = new TbItemCatExample();
		// 设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		// 执行查询
		List<TbItemCat> list = itemcatMapper.selectByExample(example);

		// 将TbItemCat的列表转换成EasyUITreeNode的列表
		List<EasyUITreeNode> result = new ArrayList<EasyUITreeNode>();
		for (TbItemCat itemCat : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(itemCat.getId());
			node.setText(itemCat.getName());
			node.setState(itemCat.getIsParent() ? "closed" : "open");
			result.add(node);
		}
		return result;
	}

	@Override
	public TbItemCat getItemCatById(Long id) {
		// TODO Auto-generated method stub
		
		TbItemCat cat=itemcatMapper.selectByPrimaryKey(id);
		
		return cat;
	}

}
