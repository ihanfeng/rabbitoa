package com.zhm.rabbit.oa.service;

import java.util.List;

import com.zhm.rabbit.oa.repositories.OaMenu;

public interface OaMenuService {

	List<OaMenu> findAll();

	List<OaMenu> findByType(int type);

}
