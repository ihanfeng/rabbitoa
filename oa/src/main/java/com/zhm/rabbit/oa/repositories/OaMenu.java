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
 * 系统资源路径表
 */
@Entity
@DynamicInsert 
@DynamicUpdate
@Table(name="oa_menu") 
public class OaMenu {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private int id;
	
	private String url;
	
	private String name;
	
	private int type;//1:显示系统权限菜单 2:只有在权限设置的时候才显示
	
	private int pid;
}
