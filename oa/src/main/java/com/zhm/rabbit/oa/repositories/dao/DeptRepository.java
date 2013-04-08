package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zhm.rabbit.oa.repositories.Department;

public interface DeptRepository extends PagingAndSortingRepository<Department, Integer> {

	List<Department> findByPid(int pid);

}
