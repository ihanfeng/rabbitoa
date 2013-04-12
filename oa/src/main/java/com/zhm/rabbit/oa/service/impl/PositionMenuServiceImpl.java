package com.zhm.rabbit.oa.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhm.rabbit.oa.repositories.PositionMenu;
import com.zhm.rabbit.oa.repositories.dao.PositionMenuRepository;
import com.zhm.rabbit.oa.service.PositionMenuService;
@Service("positionMenuService")
public class PositionMenuServiceImpl implements PositionMenuService {
	
	@Autowired
	private PositionMenuRepository dao;
	@Autowired
	private EntityManagerFactory emf;
	public List<PositionMenu> findByRoleid(int positionid)
	{
		// TODO Auto-generated method stub
		return dao.findByRoleid(positionid);
	}
	public void saveMenuids(int typeid, String menuids)
	{
		// TODO Auto-generated method stub
		EntityManager em=null;
		em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.createQuery("delete from PositionMenu d where d.roleid=?").setParameter(1, typeid).executeUpdate();
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
		String[] ary = menuids.split("\\|");
		for(String tmp:ary)
		{
			if(!"".equals(tmp))
			{
				PositionMenu menu = new PositionMenu();
				menu.setRoleid(typeid);
				menu.setMenuid(Integer.parseInt(tmp));
				dao.save(menu);
			}
		}
		
	}
	
}
