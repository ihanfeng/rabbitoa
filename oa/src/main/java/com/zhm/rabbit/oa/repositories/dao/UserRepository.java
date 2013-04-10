package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.zhm.rabbit.oa.repositories.UserInfo;

public interface UserRepository extends PagingAndSortingRepository<UserInfo, Integer> {

	List<UserInfo> findByEmail(String email);

	List<UserInfo> findByMobile(String mobile);

	@Query("select u from UserInfo u where u.mobile = :mobile or u.email = :email")
	UserInfo findByUserId(@Param("mobile")String username, @Param("email")String username2);
	
	
}
