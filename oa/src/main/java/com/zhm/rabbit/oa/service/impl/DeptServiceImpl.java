package com.zhm.rabbit.oa.service.impl;

import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.googlecode.ehcache.annotations.Cacheable;
import com.googlecode.ehcache.annotations.TriggersRemove;
import com.googlecode.ehcache.annotations.When;
import com.zhm.rabbit.oa.repositories.Department;
import com.zhm.rabbit.oa.repositories.dao.DeptRepository;
import com.zhm.rabbit.oa.service.DeptService;
@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptRepository dao;
	@Autowired
	private EntityManagerFactory emf;
	@Cacheable(cacheName="rabbitDepartment") 
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		List<Department> result=null;
		EntityManager em=null;
		try
		{
			Map<Integer,Integer> leafNodes = Maps.newHashMap();
			String sql ="SELECT t1.id as id FROM department AS t1 LEFT JOIN department as t2 ON t1.id = t2.pid WHERE t2.id IS NULL";
			em = emf.createEntityManager();
			Query q = em.createNativeQuery(sql);
			List lnodes = q.getResultList();
			for(Object tmp:lnodes)
			{
				leafNodes.put((Integer)tmp,(Integer)tmp);
			}
			result = Lists.newArrayList();
			showAllChildren("",0,result,leafNodes);
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
		return result;
	}

	/**
	 * 递归查询树状表
	 * @param pid
	 * @param level
	 * @param result
	 */
	private void showAllChildren(String pid, int level,List<Department> result,Map<Integer,Integer> leafNodes)
	{
		// TODO Auto-generated method stub
		String where = "";
		if("".equals(pid))
		{
			where = " where pid is null";
		}
		else
		{
			where = " where pid="+pid;
		}
		String sql = "select * from department"+where+" order by ordernum";
		List<Department> depts = null;
		EntityManager em = null;
		try
		{
			em = emf.createEntityManager();
			Query q = em.createNativeQuery(sql, Department.class);
			depts = q.getResultList();
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
		for(Department tmp:depts)
		{
			if(tmp.getPid()==null)
			{
				tmp.setPid("NULL");
			}
			if(leafNodes.get(tmp.getId())==null)
			{
				tmp.setLeaf(false);
			}
			else
			{
				if(tmp.getId().intValue()==leafNodes.get(tmp.getId()).intValue())
				{
					tmp.setLeaf(true);
				}
				else
				{
					tmp.setLeaf(false);
				}
			}
			tmp.setExpanded(true);
			tmp.setLevel(level);
			result.add(tmp);
			showAllChildren(tmp.getId()+"",level+1,result,leafNodes);
		}
		
	}

	public List<Department> findByPid(String pid) {
		// TODO Auto-generated method stub
		return dao.findByPid(pid);
	}

	public int findMaxOrderNumByPid(String pId) {
		// TODO Auto-generated method stub
		int result=0;
		EntityManager em=null;
		try
		{
			
			em = emf.createEntityManager();
			String sql = "select max(ordernum) from department where pid=?";
			Query q = em.createNativeQuery(sql);
			q.setParameter(1,pId);
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
	@TriggersRemove(cacheName="rabbitDepartment",when=When.AFTER_METHOD_INVOCATION,removeAll = true)
	public void save(Department dept) {
		// TODO Auto-generated method stub
		dao.save(dept);
	}

	public Department findById(Integer infoid) {
		// TODO Auto-generated method stub
		return dao.findOne(infoid);
	}
	@TriggersRemove(cacheName="rabbitDepartment",when=When.AFTER_METHOD_INVOCATION,removeAll = true)
	@Transactional(rollbackFor=Exception.class)
	public void orderDepts(int currId, int changeId) {
		// TODO Auto-generated method stub
		Department dept1 = dao.findOne(currId);
		Department dept2 = dao.findOne(changeId);
		int tmpOrderNum = dept1.getOrdernum();
		dept1.setOrdernum(dept2.getOrdernum());
		dept2.setOrdernum(tmpOrderNum);
		dao.save(dept1);
		dao.save(dept2);
	}
}
