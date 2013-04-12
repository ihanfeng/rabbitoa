package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zhm.rabbit.oa.repositories.OaMenu;

public interface OaMenuRepository extends  CrudRepository<OaMenu, Integer>{

	List<OaMenu> findByType(int type);
	
}
