package com.zhm.rabbit.oa.service;

import java.util.List;

import com.zhm.rabbit.oa.repositories.Department;

public interface DeptService {

	List<Department> findAll();

	List<Department> findByPid(int pid);

}
