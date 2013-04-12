package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zhm.rabbit.oa.repositories.PositionMenu;

public interface PositionMenuRepository extends
		CrudRepository<PositionMenu, Integer>
{

	List<PositionMenu> findByRoleid(int positionid);

}
