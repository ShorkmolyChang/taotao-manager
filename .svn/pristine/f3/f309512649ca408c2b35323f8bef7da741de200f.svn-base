package com.taotao.service;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;

public interface ItemService {
	
	TbItem getItemById(Long itemId);
	
	EasyUIDataGridResult getItemList(int page,int rows);
	
	TaotaoResult createItem(TbItem item,String desc,String itemPara);
	
	TaotaoResult getItemDescByItemId(Long id);
	
	TaotaoResult updateItemById(TbItem item);
	
	TaotaoResult updateItemDescById(TbItemDesc itemDesc);
	
	TaotaoResult instockItem(Long id);
	
	TaotaoResult reshelfItem(Long id);
	
	TaotaoResult deleteItem(Long id);
}
