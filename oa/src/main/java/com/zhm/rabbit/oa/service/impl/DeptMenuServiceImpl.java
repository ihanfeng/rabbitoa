package com.zhm.rabbit.oa.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhm.rabbit.oa.repositories.DepartmentMenu;
import com.zhm.rabbit.oa.repositories.dao.DeptMenuRepository;
import com.zhm.rabbit.oa.service.DeptMenuService;
@Transactional
@Service("deptMenuService")
public class DeptMenuServiceImpl implements DeptMenuService {
	
	@Autowired
	private DeptMenuRepository dao;
	@Autowired
	private EntityManagerFactory emf;
	public List<DepartmentMenu> findByDeptid(int deptid) {
		// TODO Auto-generated method stub
		return dao.findByDeptid(deptid);
	}
	public void saveMenuids(int typeid, String menuids) {
		// TODO Auto-generated method stub
		EntityManager em=null;
		em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.createNativeQuery("delete from dept_menu where deptid=?").setParameter(1, typeid).executeUpdate();
			String[] ary = menuids.split("\\|");
			for(String tmp:ary)
			{
				if(!"".equals(tmp))
				{
					em.createNativeQuery("insert into dept_menu(deptid,menuid) values(?,?)")
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
