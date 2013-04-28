package com.zhm.rabbit.oa.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.KeyGenerator;
import com.googlecode.ehcache.annotations.Property;
import com.zhm.rabbit.oa.repositories.OaMenu;
import com.zhm.rabbit.oa.repositories.dao.OaMenuRepository;
import com.zhm.rabbit.oa.service.OaMenuService;
@Service("oaMenuService")
@Transactional(readOnly=true)
public class OaMenuServiceImpl implements OaMenuService {

	@Autowired
	private OaMenuRepository dao;
	@Cacheable(cacheName="rabbitAllMenus") 
	public List<OaMenu> findAll() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(dao.findAll());
	}
	@Cacheable(cacheName="rabbitTypeMenus", 
	    keyGenerator = @KeyGenerator (
	            name = "ListCacheKeyGenerator",
	            properties = {
	                    @Property( name="useReflection", value="true" ),
	                    @Property( name="checkforCycles", value="true" ),
	                    @Property( name="includeMethod", value="false" )
	            }
	        )
	    )
	public List<OaMenu> findByType(int type)
	{
		// TODO Auto-generated method stub
		return dao.findByType(type);
	}
	public OaMenu findById(int menuid)
	{
		// TODO Auto-generated method stub
		return dao.findOne(menuid);
	}
	
}
