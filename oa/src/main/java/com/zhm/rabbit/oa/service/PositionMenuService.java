package com.zhm.rabbit.oa.service;

import java.util.List;

import com.zhm.rabbit.oa.repositories.PositionMenu;

public interface PositionMenuService {

	List<PositionMenu> findByRoleid(int positionid);

	void saveMenuids(int typeid, String menuids);

}
