package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.service.ItemCatService;

@Controller
@RequestMapping("/item/cat")
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;

	@RequestMapping("/list")
	@ResponseBody
	// jsp页面传入的参数名为id，该方法的参数列表的参数名为parentId,采用@RequestParam("jsp页面的参数名") 参数类型。可以加上默认值。
	// 方法的输入参数名，这样的形式进行转换
	public List<EasyUITreeNode> getItemCatList(@RequestParam(value="id",defaultValue="0") long parentId) {
		List<EasyUITreeNode> list = itemCatService.getItemCatList(parentId);
		return list;
	}

}
