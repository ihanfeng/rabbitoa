package com.zhm.rabbit.oa.service;

import java.util.List;

import com.zhm.rabbit.oa.repositories.PositionRole;

public interface PositionService {

	List<PositionRole> findAll();

	List<PositionRole> findByPid(String pid);

	int findMaxOrderNumByPid(String pId);

	void save(PositionRole dept);

	PositionRole findById(Integer infoid);

	void orderDepts(int currId, int changeId);

}
