package com.zhm.rabbit.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.zhm.rabbit.oa.model.LigerGridBean;
import com.zhm.rabbit.oa.repositories.UserInfo;
import com.zhm.rabbit.oa.service.UserService;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/userManager/listAll")
	public String listAllUsers(ModelMap model)
	{
		return "/admin/user/listAll";
	}
	@RequestMapping(value="userManager/listAll/getJson")
	public @ResponseBody LigerGridBean listAllJson(String sortname,String sortorder,int page,int pagesize)
	{
		
		Page<UserInfo> pgObj = userService.findByPage(page-1,pagesize,sortname,sortorder);
		List<UserInfo> users = Lists.newArrayList(pgObj.iterator());
		LigerGridBean result = new LigerGridBean();
		result.setRows(users);
		result.setTotal(pgObj.getTotalElements());
		return result;
	}
}
