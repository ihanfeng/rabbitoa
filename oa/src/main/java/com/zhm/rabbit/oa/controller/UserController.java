package com.zhm.rabbit.oa.controller;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhm.rabbit.oa.model.GridResultBean;
import com.zhm.rabbit.oa.model.SearchBean;
import com.zhm.rabbit.oa.repositories.UserInfo;
import com.zhm.rabbit.oa.service.UserService;
import com.zhm.rabbit.oa.utils.Md5Util;
import com.zhm.rabbit.oa.utils.Page;
import com.zhm.rabbit.oa.utils.SearchUtils;

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
	public @ResponseBody GridResultBean listAllJson(String sidx,String sord,int page,int rows,String filters)
	{
		SearchBean sb = null;
		if(filters!=null&&!"".equals(filters))
		{
			ObjectMapper objectMapper = new ObjectMapper(); 
			try
			{
				sb = objectMapper.readValue(filters, SearchBean.class);
			}
			catch (JsonParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JsonMappingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		String cond = "";
		if(sb!=null)
		{
			StringBuffer where = new StringBuffer();
			cond = SearchUtils.generateSearchCond(sb, where);
		}
		int totalNums = userService.findNumsByCond(cond);
		Page pg = new Page(page,rows,totalNums,5);
		List<UserInfo> users = userService.findByCond(page, rows, sidx, sord,cond);
		GridResultBean result = new GridResultBean();
		result.setPage(page);
		result.setRecords(pg.getTotalRecords());
		result.setTotal(pg.getTotalPages());
		result.setRows(users);
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
			dbUser.setUsername(user.getUsername());
			dbUser.setEmail(user.getEmail());
			dbUser.setMobile(user.getMobile());
			dbUser.setDeptid(user.getDeptid());
			dbUser.setPositionid(user.getPositionid());
			userService.update(dbUser);
		}
		else if(oper.equals("del"))
		{
			userService.delete(id);
		}
		return "";
	}
	@RequestMapping(value="/userManager/listByDeptid")
	public String listByDeptid(int deptid,ModelMap model)
	{
		model.addAttribute("deptid", deptid);
		return "/admin/user/listByDeptid";
	}
	@RequestMapping(value="/userManager/listByDeptid/getJson")
	public @ResponseBody GridResultBean listUsersByDeptid(int deptid,String sidx,String sord,int page,int rows,String filters)
	{
		SearchBean sb = null;
		if(filters!=null&&!"".equals(filters))
		{
			ObjectMapper objectMapper = new ObjectMapper(); 
			try
			{
				sb = objectMapper.readValue(filters, SearchBean.class);
			}
			catch (JsonParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JsonMappingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		String cond = "";
		if(sb!=null)
		{
			StringBuffer where = new StringBuffer();
			cond = SearchUtils.generateSearchCond(sb, where);
		}
		int totalNums = userService.findNumsByDeptidCond(cond,deptid);
		Page pg = new Page(page,rows,totalNums,5);
		List<UserInfo> users = userService.findByDeptidCond(page, rows, sidx, sord,cond,deptid);
		GridResultBean result = new GridResultBean();
		result.setPage(page);
		result.setRecords(pg.getTotalRecords());
		result.setTotal(pg.getTotalPages());
		result.setRows(users);
		return result;
	}
	
	
	@RequestMapping(value="/userManager/listByPositionId")
	public String listByPositionId(int positionid,ModelMap model)
	{
		model.addAttribute("positionid", positionid);
		return "/admin/user/listByPositionId";
	}
	@RequestMapping(value="/userManager/listByPositionId/getJson")
	public @ResponseBody GridResultBean listUsersByPositionId(int positionid,String sidx,String sord,int page,int rows,String filters)
	{
		SearchBean sb = null;
		if(filters!=null&&!"".equals(filters))
		{
			ObjectMapper objectMapper = new ObjectMapper(); 
			try
			{
				sb = objectMapper.readValue(filters, SearchBean.class);
			}
			catch (JsonParseException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (JsonMappingException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}  
		}
		String cond = "";
		if(sb!=null)
		{
			StringBuffer where = new StringBuffer();
			cond = SearchUtils.generateSearchCond(sb, where);
		}
		int totalNums = userService.findNumsByPositionCond(cond,positionid);
		Page pg = new Page(page,rows,totalNums,5);
		List<UserInfo> users = userService.findByPositionCond(page, rows, sidx, sord,cond,positionid);
		GridResultBean result = new GridResultBean();
		result.setPage(page);
		result.setRecords(pg.getTotalRecords());
		result.setTotal(pg.getTotalPages());
		result.setRows(users);
		return result;
	}
	
}
