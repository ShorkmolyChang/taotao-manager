package com.taotao.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUIDataGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.common.utils.HttpClientUtil;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.ItemCatService;
import com.taotao.service.ItemParamService;
import com.taotao.service.ItemService;

//商品查询Controller

@Controller
public class ItemController {

	@Autowired
	private ItemService itemService;
	@Autowired
	private ItemParamService itemParamService;
	@Autowired
	private ItemCatService itemCatService;

	@Value("${SOLR_BASE_URL}")
	private String SOLR_BASE_URL;
	@Value("${SOLR_IMPORT_ALL_URL}")
	private String SOLR_IMPORT_ALL_URL;
	@Value("${SOLR_DELETE_URL}")
	private String SOLR_DELETE_URL;
	@Value("${SOLR_UPDATE_URL}")
	private String SOLR_UPDATE_URL;

	@RequestMapping("/item/{itemId}")
	@ResponseBody
	public TbItem getItemById(@PathVariable Long itemId) {
		TbItem item = itemService.getItemById(itemId);
		return item;
	}

	@RequestMapping("/item/list")
	@ResponseBody
	public EasyUIDataGridResult getItemList(Integer page, Integer rows) {
		EasyUIDataGridResult result = itemService.getItemList(page, rows);
		return result;
	}

	@RequestMapping(value = "/item/save", method = RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem item, String desc, String itemParams) {
		TaotaoResult result = itemService.createItem(item, desc, itemParams);
		// 新增商品，需要向solr服务器发送请求
		Map<String, String> map = new HashMap<String, String>();
		TbItem item2 = (TbItem) result.getData();
		map.put("id", item2.getId() + "");
		map.put("title", item2.getTitle());
		map.put("sell_point", item2.getSellPoint());
		map.put("price", item2.getPrice() + "");
		map.put("image", item2.getImage());
		TbItemCat cat = itemCatService.getItemCatById(item2.getCid());
		map.put("category_name", cat == null ? "" : cat.getName());
		TbItemDesc desc2 = (TbItemDesc) itemService.getItemDescByItemId(item2.getId()).getData();
		map.put("item_desc", desc2 == null ? "" : desc2.getItemDesc());

		HttpClientUtil.doGet(SOLR_BASE_URL + SOLR_UPDATE_URL, map);

		return result;
	}

	// private void searchImportAll() {
	// try {
	// String result = HttpClientUtil.doGet(SOLR_BASE_URL +
	// SOLR_IMPORT_ALL_URL);
	// TaotaoResult result2 = TaotaoResult.format(result);
	// if (result2.getStatus() == 200)
	// return;
	// } catch (Exception e) {
	// // TODO: handle exception
	// e.printStackTrace();
	// }
	// }

	@RequestMapping("/rest/item/query/item/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDescByItemId(@PathVariable Long itemId) {
		TaotaoResult result = itemService.getItemDescByItemId(itemId);
		return result;
	}

	@RequestMapping("/rest/item/param/item/query/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParamItemByItemId(@PathVariable Long itemId) {
		TaotaoResult result = itemParamService.getItemParamItemByItemId(itemId);
		return result;
	}

	@RequestMapping("/rest/item/update")
	@ResponseBody
	public TaotaoResult updateItemDescParamItem(TbItem item, String desc, Long itemParamId, String itemParams) {

		itemService.updateItemById(item);

		TbItemDesc itemDesc = new TbItemDesc();
		itemDesc.setItemId(item.getId());
		itemDesc.setItemDesc(desc);
		itemService.updateItemDescById(itemDesc);

		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setId(itemParamId);
		itemParamItem.setItemId(item.getId());
		itemParamItem.setParamData(itemParams);
		itemParamService.updateItemParamItemById(itemParamItem);

		// 修改商品描述，需要向solr服务器发送重新importall的请求
		// searchImportAll();

		// 新增商品，需要向solr服务器发送请求
		Map<String, String> map = new HashMap<String, String>();
		TbItem item2 = itemService.getItemById(item.getId());
		map.put("id", item2.getId() + "");
		map.put("title", item2.getTitle());
		map.put("sell_point", item2.getSellPoint());
		map.put("price", item2.getPrice() + "");
		map.put("image", item2.getImage());
		TbItemCat cat = itemCatService.getItemCatById(item2.getCid());
		map.put("category_name", cat == null ? "" : cat.getName());
		TbItemDesc desc2 = (TbItemDesc) itemService.getItemDescByItemId(item2.getId()).getData();
		map.put("item_desc", desc2 == null ? "" : desc2.getItemDesc());

		HttpClientUtil.doGet(SOLR_BASE_URL + SOLR_UPDATE_URL, map);

		return TaotaoResult.ok();
	}

	// 商品下架
	@RequestMapping("/rest/item/instock")
	@ResponseBody
	public TaotaoResult instockItem(String ids) {

		String[] strings = ids.split(",");
		for (String id : strings) {
			itemService.instockItem(Long.parseLong(id));

			// 下架商品，更新，需要向solr服务器发送请求
			Map<String, String> map = new HashMap<String, String>();
			TbItem item2 = itemService.getItemById(Long.parseLong(id));
			map.put("id", item2.getId() + "");
			map.put("title", item2.getTitle());
			map.put("sell_point", item2.getSellPoint());
			map.put("price", item2.getPrice() + "");
			map.put("image", item2.getImage());
			TbItemCat cat = itemCatService.getItemCatById(item2.getCid());
			map.put("category_name", cat == null ? "" : cat.getName());
			TbItemDesc desc2 = (TbItemDesc) itemService.getItemDescByItemId(item2.getId()).getData();
			map.put("item_desc", desc2 == null ? "" : desc2.getItemDesc());

			HttpClientUtil.doGet(SOLR_BASE_URL + SOLR_UPDATE_URL, map);
		}
		return TaotaoResult.ok();
	}

	// 商品重新上架
	@RequestMapping("/rest/item/reshelf")
	@ResponseBody
	public TaotaoResult reshelfItem(String ids) {
		String[] strings = ids.split(",");
		for (String id : strings) {
			itemService.reshelfItem(Long.parseLong(id));

			// 上架商品，更新，需要向solr服务器发送请求
			Map<String, String> map = new HashMap<String, String>();
			TbItem item2 = itemService.getItemById(Long.parseLong(id));
			map.put("id", item2.getId() + "");
			map.put("title", item2.getTitle());
			map.put("sell_point", item2.getSellPoint());
			map.put("price", item2.getPrice() + "");
			map.put("image", item2.getImage());
			TbItemCat cat = itemCatService.getItemCatById(item2.getCid());
			map.put("category_name", cat == null ? "" : cat.getName());
			TbItemDesc desc2 = (TbItemDesc) itemService.getItemDescByItemId(item2.getId()).getData();
			map.put("item_desc", desc2 == null ? "" : desc2.getItemDesc());

			HttpClientUtil.doGet(SOLR_BASE_URL + SOLR_UPDATE_URL, map);

		}
		return TaotaoResult.ok();
	}

	// 删除商品
	@RequestMapping("/rest/item/delete")
	@ResponseBody
	public TaotaoResult deleteItem(String ids) {
		String[] strings = ids.split(",");
		for (String id : strings) {
			itemService.deleteItem(Long.parseLong(id));

			// 删除商品，需要向solr服务器发送重新importall的请求
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", id);
			HttpClientUtil.doGet(SOLR_BASE_URL + SOLR_DELETE_URL, map);
		}
		return TaotaoResult.ok();
	}

}
