package com.zhm.rabbit.oa.service;

import java.util.List;

import com.zhm.rabbit.oa.repositories.GroupUser;

public interface GroupUserService {

	int findNumsByCondAndGroupid(String cond, int groupid);

	List<GroupUser> findByCondAndGroupid(int page, int rows, String sidx,
			String sord, String cond, int groupid);

}
