package com.zhm.rabbit.oa.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zhm.rabbit.oa.repositories.Department;
import com.zhm.rabbit.oa.repositories.dao.DeptRepository;
import com.zhm.rabbit.oa.service.DeptService;
@Service("deptService")
public class DeptServiceImpl implements DeptService {
	@Autowired
	private DeptRepository dao;
	@Autowired
	private EntityManagerFactory emf;
	
	public List<Department> findAll() {
		// TODO Auto-generated method stub
		EntityManager em = emf.createEntityManager();
		String sql="SELECT  hi.id as id,CONCAT(REPEAT('    ', level - 1), CAST(hi.name AS CHAR)) AS name, hi.pid as pid, level ,hi.ordernum as ordernum "+
				"FROM    ("+
					"SELECT  find_dept_level(id) AS id, @level AS level "+
					 "FROM    ("+
					 	"SELECT  @start_with \\:= 0,"+
					 		"@id \\:= @start_with,"+
					 		"@level \\:= 0"+
					 ") vars, department"+
					 " WHERE   @id IS NOT NULL"+
					 ") ho"+
					 " JOIN    department hi"+
					 " ON      hi.id = ho.id";
		Query q = em.createNativeQuery(sql, Department.class);
		return q.getResultList();
	}

	public List<Department> findByPid(int pid) {
		// TODO Auto-generated method stub
		return dao.findByPid(pid);
	}
	
	
}
