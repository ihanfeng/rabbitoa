package com.zhm.rabbit.oa.repositories.dao;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.zhm.rabbit.oa.repositories.GroupMenu;

public interface GroupMenuRepository extends
CrudRepository<GroupMenu, Integer>
{

	List<GroupMenu> findByGroupid(int groupid);

}
