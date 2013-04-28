package com.zhm.rabbit.oa.service.impl;

import java.math.BigInteger;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zhm.rabbit.oa.repositories.Department;
import com.zhm.rabbit.oa.repositories.DepartmentMenu;
import com.zhm.rabbit.oa.repositories.GroupUser;
import com.zhm.rabbit.oa.repositories.PositionRole;
import com.zhm.rabbit.oa.repositories.UserInfo;
import com.zhm.rabbit.oa.repositories.dao.GroupUserRepository;
import com.zhm.rabbit.oa.service.GroupUserService;
@Service("groupUserService")
public class GroupUserServiceImpl implements GroupUserService {
	@Autowired
	private GroupUserRepository dao;

	@Autowired
	private EntityManagerFactory emf;
	
	public int findNumsByCondAndGroupid(String cond, int groupid) {
		// TODO Auto-generated method stub
		int result=0;
		EntityManager em=null;
		try
		{
			
			em = emf.createEntityManager();
			String sql = "select count(a.id) from oa_group_user a left join user_info b on a.userid=b.id  where a.groupid= ? "+ cond;
			Query q = em.createNativeQuery(sql);
			q.setParameter(1, groupid);
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

	public List<GroupUser> findByCondAndGroupid(int page, int rows,
			String sidx, String sord, String cond, int groupid) {
		// TODO Auto-generated method stub
		List<GroupUser> results=null;
		EntityManager em=null;
		try
		{
			em = emf.createEntityManager();
			String sql = "select a.id,a.groupid,a.userid from oa_group_user a left join user_info b on a.userid=b.id where a.groupid=? "+ cond+" order by "+sidx+" "+sord +" limit ?,?";
			Query q = em.createNativeQuery(sql, GroupUser.class);
			q.setParameter(1, groupid);
			q.setParameter(2, (page-1)*rows);
			q.setParameter(3, rows);
			results = q.getResultList();
			String deptSql = "select * from department where id=?";
			String positionSql = "select * from position_role where id=?";
			String userSql = "select * from user_info where id=?";
			for(GroupUser tmp:results)
			{
				Query userq = em.createNativeQuery(userSql, UserInfo.class);
				userq.setParameter(1, tmp.getUserid());
				UserInfo uinfo = (UserInfo)userq.getSingleResult();
				tmp.setDeptid(uinfo.getDeptid());
				tmp.setEmail(uinfo.getEmail());
				tmp.setMobile(uinfo.getMobile());
				tmp.setPositionid(uinfo.getPositionid());
				tmp.setUsername(uinfo.getUsername());
				tmp.setId(uinfo.getId());
				if(tmp.getDeptid()!=null)
				{
					Query deptq = em.createNativeQuery(deptSql, Department.class);
					deptq.setParameter(1, tmp.getDeptid());
					tmp.setDeptid(((Department)deptq.getSingleResult()).getName());
				}
				if(tmp.getPositionid()!=null)
				{
					Query positionq = em.createNativeQuery(positionSql, PositionRole.class);
					positionq.setParameter(1, tmp.getPositionid());
					tmp.setPositionid(((PositionRole)positionq.getSingleResult()).getName());
				}
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
	/**
	 * 根据组id查询用户
	 */
	public List<GroupUser> findAllByGroupid(int groupid)
	{
		// TODO Auto-generated method stub
		List<GroupUser> results=null;
		EntityManager em=null;
		try
		{
			em = emf.createEntityManager();
			String sql = "select a.id,a.groupid,a.userid from oa_group_user a left join user_info b on a.userid=b.id where a.groupid=?";
			Query q = em.createNativeQuery(sql, GroupUser.class);
			q.setParameter(1, groupid);
			results = q.getResultList();
			String deptSql = "select * from department where id=?";
			String positionSql = "select * from position_role where id=?";
			String userSql = "select * from user_info where id=?";
			for(GroupUser tmp:results)
			{
				Query userq = em.createNativeQuery(userSql, UserInfo.class);
				userq.setParameter(1, tmp.getUserid());
				UserInfo uinfo = (UserInfo)userq.getSingleResult();
				tmp.setDeptid(uinfo.getDeptid());
				tmp.setEmail(uinfo.getEmail());
				tmp.setMobile(uinfo.getMobile());
				tmp.setPositionid(uinfo.getPositionid());
				tmp.setUsername(uinfo.getUsername());
				tmp.setId(uinfo.getId());
				if(tmp.getDeptid()!=null)
				{
					Query deptq = em.createNativeQuery(deptSql, Department.class);
					deptq.setParameter(1, tmp.getDeptid());
					tmp.setDeptid(((Department)deptq.getSingleResult()).getName());
				}
				if(tmp.getPositionid()!=null)
				{
					Query positionq = em.createNativeQuery(positionSql, PositionRole.class);
					positionq.setParameter(1, tmp.getPositionid());
					tmp.setPositionid(((PositionRole)positionq.getSingleResult()).getName());
				}
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

	public void saveUsers(int groupid, String selUserids)
	{
		// TODO Auto-generated method stub
		//先删除该组所有用户
		EntityManager em=null;
		em = emf.createEntityManager();
		EntityTransaction t = em.getTransaction();
		try {
			t.begin();
			em.createNativeQuery("delete from oa_group_user where groupid=?").setParameter(1, groupid).executeUpdate();
			String[] ary = selUserids.split("\\|");
			for(String tmp:ary)
			{
				if(!"".equals(tmp))
				{
					em.createNativeQuery("insert into oa_group_user(groupid,userid) values(?,?)")
					.setParameter(1, groupid).setParameter(2, tmp).executeUpdate();
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

	public List<GroupUser> findByUserid(Integer id)
	{
		// TODO Auto-generated method stub
		return dao.findByUserid(String.valueOf(id));
	}
	
}
