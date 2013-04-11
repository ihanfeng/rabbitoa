package com.zhm.rabbit.oa.repositories;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
/**
 * 
 * @author zhmlvft
 * 系统资源路径表
 */
@Entity
@DynamicInsert 
@DynamicUpdate
@Table(name="oa_menu") 
public class OaMenu implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Integer id;
	
	private String url;
	
	private String name;
	
	private int type;//1:显示系统管理菜单 2:功能列表菜单 0:只有在权限设置的时候才显示
	
	private int pid;

	private String targettype;
	
	@Transient
	private int checked;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getPid() {
		return pid;
	}

	public void setPid(int pid) {
		this.pid = pid;
	}

	public String getTargettype()
	{
		return targettype;
	}

	public void setTargettype(String targettype)
	{
		this.targettype = targettype;
	}

	public int getChecked() {
		return checked;
	}

	public void setChecked(int checked) {
		this.checked = checked;
	}
	
}
