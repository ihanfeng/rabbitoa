package com.zhm.rabbit.oa.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.zhm.rabbit.oa.repositories.Department;
import com.zhm.rabbit.oa.repositories.UserInfo;
import com.zhm.rabbit.oa.repositories.dao.UserRepository;
import com.zhm.rabbit.oa.service.UserService;
@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserRepository dao;
	@Autowired
	private EntityManagerFactory emf;

	public List<UserInfo> findAll() {
		// TODO Auto-generated method stub
		return Lists.newArrayList(dao.findAll());
	}

	public Page<UserInfo> findByPage(int page, int pagesize, String sortname,
			String sortorder) {
		// TODO Auto-generated method stub
		PageRequest pr = new PageRequest(page, pagesize, sortorder==null?Sort.Direction.ASC:Sort.Direction.DESC,sortname==null?"id":sortname);
		return dao.findAll(pr);
	}

	public boolean findUserExistsByEmail(String email) {
		// TODO Auto-generated method stub
		List<UserInfo> users = dao.findByEmail(email);
		return (users==null||users.size()==0)?true:false;
	}

	public boolean findUserExistsByMobile(String mobile) {
		// TODO Auto-generated method stub
		List<UserInfo> users = dao.findByMobile(mobile);
		return (users==null||users.size()==0)?true:false;
	}

	public void save(UserInfo user) {
		// TODO Auto-generated method stub
		dao.save(user);
	}

	public Page<UserInfo> findByCond(int page, int rows, String sidx, String sord)
	{
		// TODO Auto-generated method stub
		sidx=sidx==null?"asc":sidx;
		PageRequest pr = new PageRequest(page-1, rows, "asc".equals(sord)?Sort.Direction.ASC:Sort.Direction.DESC,sidx==null?"id":sidx);
		return dao.findAll(pr);
	}

	public UserInfo findById(String id) {
		// TODO Auto-generated method stub
		return dao.findOne(Integer.parseInt(id));
	}

	public void update(UserInfo user) {
		// TODO Auto-generated method stub
		dao.save(user);
	}

	public void delete(String id) {
		// TODO Auto-generated method stub
		dao.delete(Integer.parseInt(id));
	}

	public int findNumsByCond(String cond) {
		// TODO Auto-generated method stub
		int result=0;
		EntityManager em=null;
		try
		{
			
			em = emf.createEntityManager();
			String sql = "select count(id) from user_info where 1=1 "+ cond;
			Query q = em.createNativeQuery(sql);
			result = (Integer)q.getSingleResult();
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

	public List<UserInfo> findByCond(int page, int rows, String sidx,
			String sord, String cond) {
		// TODO Auto-generated method stub
		List<UserInfo> results=null;
		EntityManager em=null;
		try
		{
			em = emf.createEntityManager();
			String sql = "select * from user_info where 1=1 "+ cond+" order by "+sidx+" "+sord +" limit ?,?";
			Query q = em.createNativeQuery(sql, UserInfo.class);
			q.setParameter(1, (page-1)*rows);
			q.setParameter(2, rows);
			results = q.getResultList();
			String deptSql = "select * from department where id=?";
			for(UserInfo tmp:results)
			{
				Query deptq = em.createNativeQuery(deptSql, Department.class);
				deptq.setParameter(1, tmp.getDeptid());
				tmp.setDeptid(((Department)deptq.getSingleResult()).getName());
			}
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

	public int findNumsByDeptidCond(String cond, int deptid)
	{
		// TODO Auto-generated method stub
		int result=0;
		EntityManager em=null;
		try
		{
			
			em = emf.createEntityManager();
			String sql = "select count(id) from user_info where deptid in (select id from department where FIND_IN_SET(id, getDeptsChildLst(?))) "+ cond;
			Query q = em.createNativeQuery(sql);
			q.setParameter(1,deptid);
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

	public List<UserInfo> findByDeptidCond(int page, int rows, String sidx,
			String sord, String cond, int deptid)
	{
		// TODO Auto-generated method stub
		List<UserInfo> results=null;
		EntityManager em=null;
		try
		{
			em = emf.createEntityManager();
			String sql = "select * from user_info where deptid in (select id from department where FIND_IN_SET(id, getDeptsChildLst(?))) "+ cond+" order by "+sidx+" "+sord +" limit ?,?";
			Query q = em.createNativeQuery(sql, UserInfo.class);
			q.setParameter(1, deptid);
			q.setParameter(2, (page-1)*rows);
			q.setParameter(3, rows);
			results = q.getResultList();
			String deptSql = "select * from department where id=?";
			for(UserInfo tmp:results)
			{
				Query deptq = em.createNativeQuery(deptSql, Department.class);
				deptq.setParameter(1, tmp.getDeptid());
				tmp.setDeptid(((Department)deptq.getSingleResult()).getName());
			}
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
	
}
