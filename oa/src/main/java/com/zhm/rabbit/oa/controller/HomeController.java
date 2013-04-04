package com.zhm.rabbit.oa.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HomeController {
	@RequestMapping(value="/")
	public String root()
	{
		return "redirect:/home/main.html";
	}
	@RequestMapping(value="/home/main")
	public String index()
	{
		return "/home";
	}
}
