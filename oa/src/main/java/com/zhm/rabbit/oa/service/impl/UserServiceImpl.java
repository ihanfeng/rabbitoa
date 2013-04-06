package com.zhm.rabbit.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhm.rabbit.oa.repositories.UserInfo;
import com.zhm.rabbit.oa.repositories.dao.UserRepository;
import com.zhm.rabbit.oa.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository dao;

	public List<UserInfo> findAll() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(dao.findAll());
	}

	public Page<UserInfo> findByPage(int page, int pagesize, String sortname,
			String sortorder) {
		// TODO Auto-generated method stub
		PageRequest pr = new PageRequest(page, pagesize, sortorder==null?Sort.Direction.ASC:Sort.Direction.DESC,sortname==null?"id":sortname);
		return dao.findAll(pr);
	}

	public boolean findUserExistsByEmail(String email) {
		// TODO Auto-generated method stub
		List<UserInfo> users = dao.findByEmail(email);
		return (users==null||users.size()==0)?true:false;
	}

	public boolean findUserExistsByMobile(String mobile) {
		// TODO Auto-generated method stub
		List<UserInfo> users = dao.findByMobile(mobile);
		return (users==null||users.size()==0)?true:false;
	}

	public void save(UserInfo user) {
		// TODO Auto-generated method stub
		dao.save(user);
	}
	
}
