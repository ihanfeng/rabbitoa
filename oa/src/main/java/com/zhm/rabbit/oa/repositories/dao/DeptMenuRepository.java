package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zhm.rabbit.oa.repositories.DepartmentMenu;

public interface DeptMenuRepository extends CrudRepository<DepartmentMenu, Integer>{

	List<DepartmentMenu> findByDeptid(int deptid);

}
