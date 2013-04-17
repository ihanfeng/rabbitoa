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
import com.zhm.rabbit.oa.repositories.GroupUser;
import com.zhm.rabbit.oa.repositories.UserInfo;
import com.zhm.rabbit.oa.service.GroupUserService;
import com.zhm.rabbit.oa.service.UserService;
import com.zhm.rabbit.oa.utils.Page;
import com.zhm.rabbit.oa.utils.SearchUtils;

@Controller
public class GroupUserController {
	@Autowired
	private GroupUserService groupUserService;
	@Autowired
	private UserService userService;
	@RequestMapping(value="/groupUserManager/listByGroupid")
	public String listByGroupid(int groupid,ModelMap model)
	{
		model.addAttribute("groupid", groupid);
		return "/admin/groupUser/listByGroupid";
	}
	@RequestMapping(value="/groupUserManager/listByGroupid/getJson")
	public @ResponseBody GridResultBean listUsersByGroupid(int groupid,String sidx,String sord,int page,int rows,String filters)
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
		if(groupid==10000)
		{
			//默认用户组（目前把所有用户都归类在默认用户组里面）
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
		else
		{
			if(sb!=null)
			{
				StringBuffer where = new StringBuffer();
				cond = SearchUtils.generateSearchCond(sb, where,"b");
			}
			int totalNums = groupUserService.findNumsByCondAndGroupid(cond,groupid);
			Page pg = new Page(page,rows,totalNums,5);
			List<GroupUser> users = groupUserService.findByCondAndGroupid(page, rows, sidx, sord,cond,groupid);
			GridResultBean result = new GridResultBean();
			result.setPage(page);
			result.setRecords(pg.getTotalRecords());
			result.setTotal(pg.getTotalPages());
			result.setRows(users);
			return result;
		}
	}
}
