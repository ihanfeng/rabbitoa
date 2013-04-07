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

	public Page<UserInfo> findByCond(int page, int rows, String sidx, String sord)
	{
		// TODO Auto-generated method stub
		sidx=sidx==null?"asc":sidx;
		PageRequest pr = new PageRequest(page-1, rows, "asc".equals(sord)?Sort.Direction.ASC:Sort.Direction.DESC,sidx==null?"id":sidx);
		return dao.findAll(pr);
	}

	public UserInfo findById(String id) {
		// TODO Auto-generated method stub
		return dao.findOne(Integer.parseInt(id));
	}

	public void update(UserInfo user) {
		// TODO Auto-generated method stub
		dao.save(user);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.delete(Integer.parseInt(id));
	}
	
}
