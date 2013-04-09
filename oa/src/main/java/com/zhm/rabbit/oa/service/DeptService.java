package com.zhm.rabbit.oa.service;

import java.util.List;

import com.zhm.rabbit.oa.repositories.Department;

public interface DeptService {

	List<Department> findAll();

	List<Department> findByPid(String pid);

	int findMaxOrderNumByPid(String pId);

	void save(Department dept);

	Department findById(Integer infoid);

	void orderDepts(int currId, int changeId);

}
