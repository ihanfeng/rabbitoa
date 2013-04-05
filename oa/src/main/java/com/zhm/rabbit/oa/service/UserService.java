package com.zhm.rabbit.oa.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zhm.rabbit.oa.repositories.UserInfo;

public interface UserService {

	List<UserInfo> findAll();

	Page<UserInfo> findByPage(int page, int pagesize, String sortname,
			String sortorder);

}
