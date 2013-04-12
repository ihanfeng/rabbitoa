package com.zhm.rabbit.oa.utils;

import java.util.List;

import com.google.common.collect.Lists;
import com.zhm.rabbit.oa.repositories.DepartmentMenu;
import com.zhm.rabbit.oa.repositories.GroupMenu;
import com.zhm.rabbit.oa.repositories.PositionMenu;


public class RepeatMenuConversion
{
	public static final RepeatMenuConversion obj = new RepeatMenuConversion();
	private RepeatMenuConversion(){};
	public static RepeatMenuConversion getInstance()
	{
		return obj;
	}
	public List<Integer> conversion(List<DepartmentMenu> deptMenus,List<PositionMenu> positionMenus,List<GroupMenu> groupMenus)
	{
		List<Integer> result = Lists.newArrayList();
		if(deptMenus!=null)
		{
			for(DepartmentMenu tmp:deptMenus)
			{
				result.add(Integer.parseInt(tmp.getMenuid()));
			}
		}
		if(positionMenus!=null)
		{
			for(PositionMenu tmp:positionMenus)
			{
				boolean flag = true;
				for(Integer menuid:result)
				{
					if(tmp.getMenuid().intValue()==menuid.intValue())
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					result.add(tmp.getMenuid());
				}
			}
		}
		if(groupMenus!=null)
		{
			for(GroupMenu tmp:groupMenus)
			{
				boolean flag = true;
				for(Integer menuid:result)
				{
					if(tmp.getMenuid().intValue()==menuid.intValue())
					{
						flag=false;
						break;
					}
				}
				if(flag)
				{
					result.add(tmp.getMenuid());
				}
			}
		}
		return result;
	}
}
