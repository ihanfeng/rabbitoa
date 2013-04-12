package com.zhm.rabbit.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhm.rabbit.oa.repositories.DepartmentMenu;
import com.zhm.rabbit.oa.repositories.OaMenu;
import com.zhm.rabbit.oa.repositories.PositionMenu;
import com.zhm.rabbit.oa.service.DeptMenuService;
import com.zhm.rabbit.oa.service.DeptService;
import com.zhm.rabbit.oa.service.OaMenuService;
import com.zhm.rabbit.oa.service.PositionMenuService;
import com.zhm.rabbit.oa.service.PositionService;
import com.zhm.rabbit.oa.service.UserMenuService;
import com.zhm.rabbit.oa.service.UserService;

@Controller
public class LimitsManager {
	@Autowired
	private DeptService deptService;
	@Autowired
	private PositionService positionService;
	@Autowired
	private UserService userService;
	@Autowired
	private DeptMenuService deptMenuService;
	@Autowired
	private PositionMenuService positionMenuService;
	@Autowired
	private UserMenuService userMenuService;
	@Autowired
	private OaMenuService oaMenuService;
	@RequestMapping(value="/limitsManager/list")
	public String list(ModelMap model,Integer type)
	{
		type=type==null?1:type;
		//权限分4种，按部门授权  按职位授权 按用户组授权 直接授权 依次 type=1、2、3、4
		model.addAttribute("type", type);
		if(type==1)
		{
			//找出所有部门
			return "/admin/limits/listByDept";
		}
		else if(type==2)
		{
			//找出所有的职位
			return "/admin/limits/listByPosition";
		}
		else if(type==3)
		{
			return null;
		}
		else
		{
			return "/admin/limits/listByUser";
		}
	}
	/**
	 * 根据deptid查找权限
	 * @return
	 */
	@RequestMapping(value="/limitsManager/listByDeptid")
	public String listByDeptid(int deptid,ModelMap model)
	{
		List<DepartmentMenu> deptMenus = deptMenuService.findByDeptid(deptid);
		List<OaMenu> menus = oaMenuService.findAll();
		for(OaMenu tmp1:menus)
		{
			boolean flag = true;
			for(DepartmentMenu tmp2:deptMenus)
			{
				if(tmp1.getId()==Integer.parseInt(tmp2.getMenuid()))
				{
					tmp1.setChecked(1);
					flag=false;
					break;
				}
			}
			if(flag)
			{
				tmp1.setChecked(0);
			}
		}
		model.addAttribute("menus", menus);
		return "/admin/menus/listByTypeid";
	}
	@RequestMapping(value="/limitsManager/listByPositionId")
	public String listByPositionId(int positionid,ModelMap model)
	{
		List<PositionMenu> positionMenus = positionMenuService.findByRoleid(positionid);
		List<OaMenu> menus = oaMenuService.findAll();
		for(OaMenu tmp1:menus)
		{
			boolean flag = true;
			for(PositionMenu tmp2:positionMenus)
			{
				if(tmp1.getId()==tmp2.getMenuid())
				{
					tmp1.setChecked(1);
					flag=false;
					break;
				}
			}
			if(flag)
			{
				tmp1.setChecked(0);
			}
		}
		model.addAttribute("menus", menus);
		return "/admin/menus/listByTypeid";
	}
	@RequestMapping(value="/lismitsManager/saveLimits")
	public @ResponseBody String saveLimits(int typeid,String menuids,int type)
	{
		if(type==1)
		{
			//部门菜单
			deptMenuService.saveMenuids(typeid,menuids);
		}
		if(type==2)
		{
			positionMenuService.saveMenuids(typeid,menuids);
		}
		return "";
	}
	
}
