package com.zhm.rabbit.oa.service;

import java.util.List;

import com.zhm.rabbit.oa.repositories.GroupMenu;

public interface GroupMenuService
{

	List<GroupMenu> findByGroupid(int groupid);

	void saveMenuids(int typeid, String menuids);

}
