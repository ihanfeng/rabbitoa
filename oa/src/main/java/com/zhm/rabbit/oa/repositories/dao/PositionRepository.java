package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zhm.rabbit.oa.repositories.PositionRole;

public interface PositionRepository extends PagingAndSortingRepository<PositionRole, Integer> {

	List<PositionRole> findByPid(String pid);

}
