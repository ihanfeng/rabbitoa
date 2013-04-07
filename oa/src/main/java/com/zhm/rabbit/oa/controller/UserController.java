package com.zhm.rabbit.oa.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhm.rabbit.oa.model.GridResultBean;
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
	public @ResponseBody GridResultBean listAllJson(String sidx,String sord,int page,int rows)
	{
		
		Page<UserInfo> users = userService.findByCond(page,rows,sidx,sord);
		GridResultBean result = new GridResultBean();
		result.setPage(page);
		result.setRecords(users.getNumberOfElements());
		result.setTotal(users.getTotalPages());
		result.setRows(users.getContent());
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
	@RequestMapping(value="/userManager/editData")
	public @ResponseBody String  editData(UserInfo user,String oper,String id)
	{
		if(oper.equals("add"))
		{
			user.setId(null);
			user.setRole("1");
			user.setPassword(Md5Util.stringByMD5("123456"));
			userService.save(user);
		}
		else if(oper.equals("edit"))
		{
			UserInfo dbUser = userService.findById(id);
			dbUser.setEmail(user.getEmail());
			dbUser.setMobile(user.getMobile());
			dbUser.setDeptid(user.getDeptid());
			userService.update(user);
		}
		else if(oper.equals("del"))
		{
			userService.delete(id);
		}
		return "";
	}
}
