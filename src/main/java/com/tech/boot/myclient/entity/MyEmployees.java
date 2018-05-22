package com.tech.boot.myclient.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "MY_EMPLOYEES")
public class MyEmployees {

	@Id
	@Column(name = "EMP_ID")
	private Integer employeeId;

	@Column(name = "USER_NAME")
	private String userName;

	@Column(name = "PASSWORD")
	private String password;

	public Integer getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Integer employeeId) {
		this.employeeId = employeeId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public MyEmployees() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MyEmployees(Integer employeeId, String userName, String password) {
		super();
		this.employeeId = employeeId;
		this.userName = userName;
		this.password = password;
	}
	
	

}
