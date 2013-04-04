package com.zhm.rabbit.oa.repositories;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
/**
 * 
 * @author zhmlvft
 *  部门角色用户关系表
 */
@Entity
@DynamicInsert 
@DynamicUpdate
@Table(name="dept_user_role") 
public class DepartmentUserRole {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	private int deptroleid;
	
	private String userid;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getDeptroleid() {
		return deptroleid;
	}

	public void setDeptroleid(int deptroleid) {
		this.deptroleid = deptroleid;
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}
	
}
