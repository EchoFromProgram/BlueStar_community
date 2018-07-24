package com.bluestar.teach.entity;

import java.util.List;

public class UserData {
	
	private String password;
	
	private String role;
	
	private Integer roleId;
	
	private Integer userId;
	
	private Integer typeId;
	
	private List<Clazz> classNames;
	
	private String username;
	
	private String name;

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	public List<Clazz> getClassNames() {
		return classNames;
	}

	public void setClassNames(List<Clazz> classNames) {
		this.classNames = classNames;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	@Override
	public String toString() {
		return "UserData [password=" + password + ", role=" + role + ", roleId=" + roleId + ", userId=" + userId
				+ ", typeId=" + typeId + ", classNames=" + classNames + ", username=" + username + ", name=" + name
				+ "]";
	}

	

	
	
	


	
}
