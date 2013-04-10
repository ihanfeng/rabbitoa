package com.zhm.rabbit.oa.service;

import java.util.List;

import org.springframework.data.domain.Page;

import com.zhm.rabbit.oa.repositories.UserInfo;

public interface UserService {

	List<UserInfo> findAll();

	Page<UserInfo> findByPage(int page, int pagesize, String sortname,
			String sortorder);

	boolean findUserExistsByEmail(String email);

	boolean findUserExistsByMobile(String mobile);

	void save(UserInfo user);

	Page<UserInfo> findByCond(int page, int rows, String sidx, String sord);

	UserInfo findById(String id);

	void update(UserInfo user);

	void delete(String id);

	int findNumsByCond(String cond);

	List<UserInfo> findByCond(int page, int rows, String sidx, String sord,
			String cond);

	int findNumsByDeptidCond(String cond, int deptid);

	List<UserInfo> findByDeptidCond(int page, int rows, String sidx,
			String sord, String cond, int deptid);

	int findNumsByPositionCond(String cond, int positionid);

	List<UserInfo> findByPositionCond(int page, int rows, String sidx,
			String sord, String cond, int positionid);

	UserInfo findByUserId(String username);

}
