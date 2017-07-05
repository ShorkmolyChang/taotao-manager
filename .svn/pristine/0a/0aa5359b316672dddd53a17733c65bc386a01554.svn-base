package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.mapper.TbItemParamMapper;
import com.taotao.pojo.TbItemParam;
import com.taotao.pojo.TbItemParamExample;
import com.taotao.pojo.TbItemParamExample.Criteria;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.service.ItemParamService;

@Service
public class ItemParamServiceImpl implements ItemParamService {

	@Autowired
	private TbItemParamMapper itemParamMapper;

	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	@Override
	public EasyUIDataGridResult getItemList(int page, int rows) {
		// TODO Auto-generated method stub

		// 分页处理
		PageHelper.startPage(page, rows);
		// 执行查询
		TbItemParamExample example = new TbItemParamExample();
		// ************************************warnning******************************************
		// 要用selectByExampleWithBLOBs，不能用selectByExample，否则mysql数据库中text类型的字段取不出来
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(example);
		PageInfo<TbItemParam> pageInfo = new PageInfo<TbItemParam>(list);

		// 返回查询结果
		EasyUIDataGridResult result = new EasyUIDataGridResult();
		result.setTotal(pageInfo.getTotal());
		result.setRows(pageInfo.getList());
		return result;
	}

	@Override
	public TaotaoResult getItemParaByCid(long cid) {
		// TODO Auto-generated method stub
		// 创建查询条件
		TbItemParamExample itemParamExample = new TbItemParamExample();
		Criteria criteria = itemParamExample.createCriteria();
		criteria.andItemCatIdEqualTo(cid);
		// 执行查询
		// selectByExample:只能得到表中的基本类型属性，blob属性取不到
		// List<TbItemParam>
		// list=itemParamMapper.selectByExample(itemParamExample);
		// selectByExampleWithBLOBs：可以得到元组中的所有属性
		List<TbItemParam> list = itemParamMapper.selectByExampleWithBLOBs(itemParamExample);
		// 判断是否得到查询结果
		if (list != null && list.size() > 0) {
			TbItemParam itemParam = list.get(0);
			return TaotaoResult.ok(itemParam);
		}
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult insertItemPara(Long cid, String paramData) {
		// TODO Auto-generated method stub
		TbItemParam itemParam = new TbItemParam();
		itemParam.setItemCatId(cid);
		itemParam.setParamData(paramData);
		Date date = new Date();
		itemParam.setUpdated(date);
		itemParam.setCreated(date);
		// 插入记录
		itemParamMapper.insert(itemParam);
		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult deleteItemPara(Long id) {
		// TODO Auto-generated method stub

		itemParamMapper.deleteByPrimaryKey(id);

		return TaotaoResult.ok();
	}

	@Override
	public TaotaoResult getItemParamItemByItemId(Long id) {
		// TODO Auto-generated method stub

		TbItemParamItemExample example = new TbItemParamItemExample();
		com.taotao.pojo.TbItemParamItemExample.Criteria criteria = example.createCriteria();
		criteria.andItemIdEqualTo(id);

		List<TbItemParamItem> list = itemParamItemMapper.selectByExampleWithBLOBs(example);

		if (list == null || list.size() <= 0)
			return TaotaoResult.ok();

		return TaotaoResult.ok(list.get(0));
	}

	@Override
	public TaotaoResult updateItemParamItemById(TbItemParamItem itemParamItem) {
		// TODO Auto-generated method stub

		itemParamItem.setUpdated(new Date());
		// update必须将要更新的itemDesc的所有字段都重新设置一遍，无论有没有被修改
		// 否则会报错，null
		itemParamItem.setCreated(itemParamItemMapper.selectByPrimaryKey(itemParamItem.getId()).getCreated());

		itemParamItemMapper.updateByPrimaryKeyWithBLOBs(itemParamItem);

		return TaotaoResult.ok();
	}

}
