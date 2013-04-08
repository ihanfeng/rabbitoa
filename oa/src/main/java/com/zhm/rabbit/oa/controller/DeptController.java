package com.zhm.rabbit.oa.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhm.rabbit.oa.model.GridResultBean;
import com.zhm.rabbit.oa.repositories.Department;
import com.zhm.rabbit.oa.service.DeptService;

@Controller
public class DeptController {
	@Autowired
	private DeptService deptService;
	@RequestMapping(value="/deptManager/listAll")
	public String listAll()
	{
		return "/admin/dept/listAll";
	}
	@RequestMapping(value="/deptManager/listAll/getJson")
	public @ResponseBody GridResultBean listJson(String sidx,String sord,int page,int rows)
	{
		List<Department> depts = deptService.findAll();
		for(Department tmp:depts)
		{
			List<Department> existsDepts = deptService.findByPid(tmp.getId());
			if(existsDepts!=null&&existsDepts.size()>0)
			{
				tmp.setLeaf(false);
				tmp.setExpanded(false);
			}
			else
			{
				tmp.setLeaf(true);
				tmp.setExpanded(false);
			}
			if(tmp.getLevel()==0)
			{
				tmp.setParent("null");
			}
			else
			{
				tmp.setParent(tmp.getPid()+"");
			}
		}
		GridResultBean result = new GridResultBean();
		result.setPage(1);
		result.setRecords(depts.size());
		result.setTotal(1);
		result.setRows(depts);
		return result;
	}
}
