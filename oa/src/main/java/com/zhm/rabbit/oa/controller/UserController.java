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
import com.zhm.rabbit.oa.utils.Md5Util;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	@RequestMapping(value="/userManager/listAll")
	public String listAllUsers(ModelMap model)
	{
		return "/admin/user/listAll";
	}
	@RequestMapping(value="/userManager/listAll/getJson")
	public @ResponseBody LigerGridBean listAllJson(String sortname,String sortorder,int page,int pagesize)
	{
		
		Page<UserInfo> pgObj = userService.findByPage(page-1,pagesize,sortname,sortorder);
		List<UserInfo> users = Lists.newArrayList(pgObj.iterator());
		LigerGridBean result = new LigerGridBean();
		result.setRows(users);
		result.setTotal(pgObj.getTotalElements());
		return result;
	}
	@RequestMapping(value="/userManager/preAdd")
	public String preAdd()
	{
		return "/admin/user/add";
	}
	/**
	 * 
	 * @return
	 * 验证邮箱地址是否已经存在
	 */
	@RequestMapping(value="/userManager/validateUserEmailExists")
	public @ResponseBody String validateUserEmailExists(String email)
	{
		boolean isExists = userService.findUserExistsByEmail(email);
		return String.valueOf(isExists);
	}
	/**
	 * 验证手机号是否存在
	 * @param mobile
	 * @return
	 */
	@RequestMapping(value="/userManager/validateUserMobileExists")
	public @ResponseBody String validateUserMobileExists(String mobile)
	{
		boolean isExists = userService.findUserExistsByMobile(mobile);
		return String.valueOf(isExists);
	}
	@RequestMapping(value="/userManager/add")
	public String addUser(UserInfo user)
	{
		user.setRole("1");
		user.setPassword(Md5Util.stringByMD5("123456"));
		userService.save(user);
		return "redirect:/userManager/listAll";
	}
}
