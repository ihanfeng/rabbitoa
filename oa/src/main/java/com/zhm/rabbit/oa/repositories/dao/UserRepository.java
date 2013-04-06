package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.zhm.rabbit.oa.repositories.UserInfo;

public interface UserRepository extends PagingAndSortingRepository<UserInfo, Long> {

	List<UserInfo> findByEmail(String email);

	List<UserInfo> findByMobile(String mobile);
	
}
