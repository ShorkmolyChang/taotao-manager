package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParamItem;

public interface ItemParamService {
	
	EasyUIDataGridResult getItemList(int page,int rows); 
	
	TaotaoResult getItemParaByCid(long cid);
	
	TaotaoResult insertItemPara(Long cid,String paramData);
	
	TaotaoResult deleteItemPara(Long id);
	
	TaotaoResult getItemParamItemByItemId(Long id);
	
	
	TaotaoResult updateItemParamItemById(TbItemParamItem itemParamItem);
	
}
