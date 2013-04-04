package com.zhm.rabbit.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhm.rabbit.oa.dao.OaMenuRepository;
import com.zhm.rabbit.oa.repositories.OaMenu;
import com.zhm.rabbit.oa.service.OaMenuService;
@Service("oaMenuService")
public class OaMenuServiceImpl implements OaMenuService {

	@Autowired
	private OaMenuRepository dao;
	public List<OaMenu> findAll() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(dao.findAll());
	}

}
