package com.zhm.rabbit.oa.repositories.dao;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zhm.rabbit.oa.repositories.UserInfo;

public interface UserRepository extends PagingAndSortingRepository<UserInfo, Long> {
	
}
