package com.zhm.rabbit.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhm.rabbit.oa.repositories.OaMenu;
import com.zhm.rabbit.oa.service.OaMenuService;

@Controller
public class HomeController {
	@Autowired
	private OaMenuService oaMenuService;
	@RequestMapping(value="/")
	public String root()
	{
		return "redirect:/home/main.html";
	}
	@RequestMapping(value="/home/main")
	public String index(ModelMap model)
	{
		List<OaMenu> menus = oaMenuService.findAll();
		model.addAttribute("menus", menus);
		return "/home";
	}
}
