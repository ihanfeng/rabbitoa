package com.zhm.rabbit.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.zhm.rabbit.oa.service.OaMenuService;

@Controller
public class OaMenuController {
	@Autowired
	private OaMenuService oaMenuService;
	
}
