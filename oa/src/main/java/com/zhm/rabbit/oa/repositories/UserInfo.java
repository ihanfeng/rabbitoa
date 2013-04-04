package com.zhm.rabbit.oa.repositories;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="user_info") 
public class UserInfo {
	  @Id
	  @GeneratedValue(strategy = GenerationType.AUTO)
	  @Column(name = "id")
	  private Long id;
	  
	  @Column(unique = true)
	  private String userid;

	  private String password;
	  
	  private String username;
	  
	  private String role;
	  
	  private int deptid;
	  
	  private String mobile;
	  
	  private String email;
	  
	  
}
