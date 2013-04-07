package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import com.zhm.rabbit.oa.repositories.OaMenu;

public interface OaMenuRepository extends  CrudRepository<OaMenu, Long>{

	List<OaMenu> findByType(int type);
	
}
