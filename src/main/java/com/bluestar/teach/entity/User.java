package com.bluestar.teach.entity;

import java.util.List;

import javax.validation.constraints.Pattern;

/**
 * 用户类，对应user表
 * @author happyChicken
 *
 */
public class User {

	//id
	private Integer userId;
	
	//用户名
	@Pattern(regexp = "(^[a-zA-Z0-9_-]{1,63}$)", message = "用户名必须是1~63位字母或数字")
	private String userName;
	
	//密码
	@Pattern(regexp = "(^[a-zA-Z0-9_-]{1,63}$)", message = "密码必须是1~63位字母或数字")
	private String password;
	
	//姓名
	@Pattern(regexp = "(^[a-zA-Z]{2,10}$)|(^[\\u2E80-\\u9FFF]{2,5})",
   		 message = "姓名必须是2~10位字母或者2~5位中文")
	private String name;
	
	//角色id
	private Integer roleId;
	
	//信息id
	private Integer infoId;
	
	//用户类型
	private Integer typeId;
	
	

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
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

	public Integer getInfoId() {
		return infoId;
	}

	public void setInfoId(Integer infoId) {
		this.infoId = infoId;
	}

	public Integer getTypeId() {
		return typeId;
	}

	public void setTypeId(Integer typeId) {
		this.typeId = typeId;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", password=" + password + ", name=" + name
				+ ", roleId=" + roleId + ", infoId=" + infoId + ", typeId=" + typeId + "]";
	}


	
	
}
