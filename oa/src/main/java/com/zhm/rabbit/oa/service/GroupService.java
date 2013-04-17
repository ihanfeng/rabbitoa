package com.zhm.rabbit.oa.service;

import java.util.List;

import com.zhm.rabbit.oa.repositories.Group;

public interface GroupService {

	int findNumsByCond(String cond);

	List<Group> findByCond(int page, int rows, String sidx, String sord,
			String cond);

	void save(Group group);

	Group findById(String id);

	void update(Group dbGroup);

	void delete(String id);


}
