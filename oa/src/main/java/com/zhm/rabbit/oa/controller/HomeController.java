package com.zhm.rabbit.oa.controller;

import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.collect.Lists;
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
		Subject subject = SecurityUtils.getSubject();
		List<OaMenu> adminMenus = oaMenuService.findByType(1);
		if(subject.hasRole("ROLE_ADMIN"))
		{
			model.addAttribute("adminMenus", adminMenus);
			return "/home";
		}
		List<OaMenu> resultMenus = Lists.newArrayList();
		for(OaMenu tmp:adminMenus)
		{
			if(subject.isPermitted(tmp.getUrl()))
			{
				resultMenus.add(tmp);
			}
		}
		model.addAttribute("adminMenus", resultMenus);
		return "/home";
	}
	@RequestMapping(value="/home/deskPage")
	public String deskPage()
	{
		return "/common/innerIndex";
	}
}
