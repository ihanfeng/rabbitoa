package com.zhm.rabbit.oa.controller;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhm.rabbit.oa.model.GridResultBean;
import com.zhm.rabbit.oa.repositories.PositionRole;
import com.zhm.rabbit.oa.service.PositionService;

@Controller
public class PositionRoleController {
	@Autowired
	private PositionService positionService;
	@RequestMapping(value="/positionManager/listAll")
	public String listAll()
	{
		return "/admin/position/listAll";
	}
	@RequestMapping(value="/positionManager/listAll/getJson")
	public @ResponseBody GridResultBean listJson(String sidx,String sord,int page,int rows)
	{
		List<PositionRole> proles = positionService.findAll();
		GridResultBean result = new GridResultBean();
		result.setPage(1);
		result.setRecords(proles.size());
		result.setTotal(1);
		result.setRows(proles);
		return result;
	}
	@RequestMapping(value="/positionManager/editPositionTree")
	public String editDeptTree(ModelMap model)
	{
		List<PositionRole> proles = positionService.findAll();
		model.addAttribute("proles", proles);
		return "/admin/position/editPositionTree";
		
	}
	@RequestMapping(value="/positionManager/addAjaxMenu")
	public @ResponseBody String addAjaxMenu(String pId,String name)
	{
		int maxOrderNum = positionService.findMaxOrderNumByPid(pId);
		PositionRole prole = new PositionRole();
		prole.setName(name);
		prole.setPid(pId);
		prole.setOrdernum(maxOrderNum+1);
		positionService.save(prole);
		return "";
	}
	@RequestMapping(value="/positionManager/modMenus")
	public @ResponseBody String modMenu(Integer infoid,String name)
	{
		PositionRole prole= positionService.findById(infoid);
		prole.setName(name);
		positionService.save(prole);
		return "";
	}
	@RequestMapping(value="/positionManager/orderTypes")
	public @ResponseBody String orderTypes(int currId,int changeId)
	{
		positionService.orderDepts(currId,changeId);
		return "";
	}
}
