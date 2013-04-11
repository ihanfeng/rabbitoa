package com.zhm.rabbit.oa.service;

import java.util.List;

import com.zhm.rabbit.oa.repositories.DepartmentMenu;

public interface DeptMenuService {

	List<DepartmentMenu> findByDeptid(int deptid);

	void saveMenuids(int typeid, String menuids);

}
