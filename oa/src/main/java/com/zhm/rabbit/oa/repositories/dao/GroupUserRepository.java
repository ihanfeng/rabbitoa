package com.zhm.rabbit.oa.repositories.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zhm.rabbit.oa.repositories.GroupUser;

public interface GroupUserRepository extends
		PagingAndSortingRepository<GroupUser, Integer> {

}
