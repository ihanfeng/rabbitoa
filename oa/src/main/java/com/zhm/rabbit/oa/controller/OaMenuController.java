package com.zhm.rabbit.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhm.rabbit.oa.repositories.OaMenu;
import com.zhm.rabbit.oa.service.OaMenuService;

@Controller
public class OaMenuController {
	@Autowired
	private OaMenuService oaMenuService;
	
}
