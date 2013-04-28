package com.zhm.rabbit.oa.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhm.rabbit.oa.repositories.GroupMenu;
import com.zhm.rabbit.oa.repositories.dao.GroupMenuRepository;
import com.zhm.rabbit.oa.service.GroupMenuService;
@Service("groupMenuService")
public class GroupMenuServiceImpl implements GroupMenuService
{
	@Autowired
	private GroupMenuRepository dao;
	@Autowired
	private EntityManagerFactory emf;
	public List<GroupMenu> findByGroupid(int groupid)
	{
		// TODO Auto-generated method stub
		return dao.findByGroupid(groupid);
	}

	public void saveMenuids(int typeid, String menuids)
	{
		// TODO Auto-generated method stub
		EntityManager em=null;
		em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.createNativeQuery("delete from oa_group_menu where groupid=?").setParameter(1, typeid).executeUpdate();
			String[] ary = menuids.split("\\|");
			for(String tmp:ary)
			{
				if(!"".equals(tmp))
				{
					em.createNativeQuery("insert into oa_group_menu(groupid,menuid) values(?,?)")
					.setParameter(1, typeid).setParameter(2, tmp).executeUpdate();
				}
			}
			t.commit();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			t.rollback();
			e.printStackTrace();
		}finally
		{
			if(em!=null)
			{
				em.close();
			}
		}
	}
	
	
}
