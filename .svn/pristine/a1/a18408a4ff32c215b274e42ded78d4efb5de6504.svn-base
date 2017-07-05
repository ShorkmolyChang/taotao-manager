package com.taotao.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

//页面跳转controller

@Controller
public class PageController {

	@RequestMapping("/")
	public String showIndex() {
		return "index";
	}

	// controller是指一个类，handler是指一个controller中的类
//	@PathVariable后面的变量是从url中获取的page参数
	@RequestMapping("/{page}")
	public String showPage(@PathVariable String page) {
		return page;
	}
}
