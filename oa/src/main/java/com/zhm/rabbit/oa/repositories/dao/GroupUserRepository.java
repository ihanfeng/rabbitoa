package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zhm.rabbit.oa.repositories.GroupUser;

public interface GroupUserRepository extends
		PagingAndSortingRepository<GroupUser, Integer> {

	List<GroupUser> findByUserid(String id);

}
