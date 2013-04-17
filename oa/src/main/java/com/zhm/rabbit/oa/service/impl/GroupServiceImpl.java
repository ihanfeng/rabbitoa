package com.zhm.rabbit.oa.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhm.rabbit.oa.repositories.Group;
import com.zhm.rabbit.oa.repositories.dao.GroupRepository;
import com.zhm.rabbit.oa.service.GroupService;
@Service("groupService")
public class GroupServiceImpl implements GroupService {
	@Autowired
	private GroupRepository dao;
	@Autowired
	private EntityManagerFactory emf;
	public int findNumsByCond(String cond) {
		// TODO Auto-generated method stub
		int result=0;
		EntityManager em=null;
		try
		{
			
			em = emf.createEntityManager();
			String sql = "select count(id) from oa_group where 1=1 "+ cond;
			Query q = em.createNativeQuery(sql);
			result = ((BigInteger)q.getSingleResult()).intValue();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally
		{
			if(em!=null)
			{
				em.close();
			}
		}
		return result;
	}

	public List<Group> findByCond(int page, int rows, String sidx, String sord,
			String cond) {
		// TODO Auto-generated method stub
		List<Group> results=null;
		EntityManager em=null;
		try
		{
			em = emf.createEntityManager();
			String sql = "select * from oa_group where 1=1 "+ cond+" order by "+sidx+" "+sord +" limit ?,?";
			Query q = em.createNativeQuery(sql, Group.class);
			q.setParameter(1, (page-1)*rows);
			q.setParameter(2, rows);
			results = q.getResultList();
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			if(em!=null)
			{
				em.close();
			}
		}
		return results;
	}

	public void save(Group group) {
		// TODO Auto-generated method stub
		dao.save(group);
	}

	public Group findById(String id) {
		// TODO Auto-generated method stub
		return dao.findOne(Integer.parseInt(id));
	}

	public void update(Group dbGroup) {
		// TODO Auto-generated method stub
		dao.save(dbGroup);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.delete(Integer.parseInt(id));
	}
	
}
