package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.IDUtils;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemService;

@Service
public class ItemServiceImpl implements ItemService {

	@Autowired
	private TbItemMapper itemMapper;
	@Autowired
	private TbItemDescMapper itemDescMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public TbItem getItemById(Long itemId) {
		// TODO Auto-generated method stub
		// TbItem item=itemMapper.selectByPrimaryKey(itemId);

		TbItemExample tbItemExample = new TbItemExample();
		// 创建查询条件
		Criteria criteria = tbItemExample.createCriteria();
		criteria.andIdEqualTo(itemId);
		List<TbItem> list = itemMapper.selectByExample(tbItemExample);
		TbItem item = null;
		if (list != null && list.size() != 0) {
			item = list.get(0);
		}
		return item;
	}

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		// TODO Auto-generated method stub
		// 分页处理
		PageHelper.startPage(page, rows);

		// 执行查询
		TbItemExample example = new TbItemExample();
		List<TbItem> list = itemMapper.selectByExample(example);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);

		// 返回查询结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		return result;
	}

	@Override
	public TaotaoResult createItem(TbItem item, String desc, String itemPara) {
		// TODO Auto-generated method stub
		// 生成商品id
		long itemId = IDUtils.genItemId();
		item.setId(itemId);
		// 商品状态 1-正常， 2-下架， 3-删除
		item.setStatus((byte) 1);
		// 创建时间和更新时间
		Date date = new Date();
		item.setCreated(date);
		item.setUpdated(date);
		// 插入商品表
		itemMapper.insert(item);

		// 商品描述
		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(itemId);
		itemDesc.setItemDesc(desc);
		itemDesc.setCreated(date);
		itemDesc.setUpdated(date);
		itemDescMapper.insert(itemDesc);

		// 添加商品规格参数的处理
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemPara);
		itemParamItem.setCreated(date);
		itemParamItem.setUpdated(date);
		// 插入数据
		itemParamItemMapper.insert(itemParamItem);
		
		
		
		return TaotaoResult.ok(getItemById(itemId));
	}

	@Override
	public TaotaoResult getItemDescByItemId(Long id) {
		// TODO Auto-generated method stub

		TbItemDesc itemDesc = itemDescMapper.selectByPrimaryKey(id);
		return TaotaoResult.ok(itemDesc);
	}

	@Override
	public TaotaoResult updateItemById(TbItem item) {
		// TODO Auto-generated method stub

		item.setUpdated(new Date());

		TbItem item2 = getItemById(item.getId());
//		update必须将要更新的itemDesc的所有字段都重新设置一遍，无论有没有被修改
//		否则会报错，null
		item.setStatus(item2.getStatus());
		item.setCreated(item2.getCreated());

		itemMapper.updateByPrimaryKey(item);

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult updateItemDescById(TbItemDesc itemDesc) {
		// TODO Auto-generated method stub

		itemDesc.setUpdated(new Date());
//		update必须将要更新的itemDesc的所有字段都重新设置一遍，无论有没有被修改
//		否则会报错，null
		itemDesc.setCreated(itemDescMapper.selectByPrimaryKey(itemDesc.getItemId()).getCreated());
		itemDescMapper.updateByPrimaryKeyWithBLOBs(itemDesc);

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult instockItem(Long id) {
		// TODO Auto-generated method stub
		
		
		TbItem item=itemMapper.selectByPrimaryKey(id);
		if(item!=null){
			// 商品状态 1-正常， 2-下架， 3-删除
			item.setStatus((byte)2);
			itemMapper.updateByPrimaryKey(item);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult reshelfItem(Long id) {
		// TODO Auto-generated method stub
		TbItem item=itemMapper.selectByPrimaryKey(id);
		if(item!=null){
			// 商品状态 1-正常， 2-下架， 3-删除
			item.setStatus((byte)1);
			itemMapper.updateByPrimaryKey(item);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItem(Long id) {
		// TODO Auto-generated method stub
		TbItem item=itemMapper.selectByPrimaryKey(id);
		if(item!=null){
			// 商品状态 1-正常， 2-下架， 3-删除
			item.setStatus((byte)3);
			itemMapper.updateByPrimaryKey(item);
		}
		return TaotaoResult.ok();
	}
}
