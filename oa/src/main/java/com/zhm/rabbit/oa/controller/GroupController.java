package com.zhm.rabbit.oa.controller;

import java.io.IOException;
import java.util.List;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zhm.rabbit.oa.model.GridResultBean;
import com.zhm.rabbit.oa.model.SearchBean;
import com.zhm.rabbit.oa.repositories.Group;
import com.zhm.rabbit.oa.service.GroupService;
import com.zhm.rabbit.oa.utils.SearchUtils;
/**
 * 用户组管理
 * @author zhmlvft
 *
 */
@Controller
public class GroupController {
	@Autowired
	private GroupService groupService;
	@RequestMapping(value="/groupManager/listAll")
	public String listAll()
	{
		return "/admin/group/listAll";
	}
	@RequestMapping(value="/groupManager/listAll/getJson")
	public @ResponseBody GridResultBean listJson(String sidx,String sord,int page,int rows,String filters)
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
		int totalNums = groupService.findNumsByCond(cond);
		List<Group> groups = groupService.findByCond(page, rows, sidx, sord,cond);
		GridResultBean result = new GridResultBean();
		result.setPage(1);
		result.setRecords(groups.size());
		result.setTotal(1);
		result.setRows(groups);
		return result;
	}
	/**
	 * 编辑组
	 * @param group
	 * @param oper
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/groupManager/editData")
	public @ResponseBody String  editData(Group group,String oper,String id)
	{
		if(oper.equals("add"))
		{
			group.setId(null);
			groupService.save(group);
		}
		else if(oper.equals("edit"))
		{
			Group dbGroup = groupService.findById(id);
			dbGroup.setName(group.getName());
			groupService.update(dbGroup);
		}
		else if(oper.equals("del"))
		{
			groupService.delete(id);
		}
		return "";
	}
}
