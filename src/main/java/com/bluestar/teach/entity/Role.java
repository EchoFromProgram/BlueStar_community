package com.bluestar.teach.entity;

import java.util.List;

import javax.validation.constraints.Pattern;

/**
 * 角色类，对应role表
 * @author happyChicken
 *
 */
public class Role {
	
	//角色id
	private Integer roleId;
	
	//角色
	@Pattern(regexp = "(^[a-zA-Z0-9_-]{1,8}$)|(^[\\u2E80-\\u9FFF]{1,8})",
   		 message = "角色名必须是1~8位的字母或中文")
	private String role;
	
	
	//权限名集合
	private List<RolePowerName> powerNames;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}


	public List<RolePowerName> getPowerNames() {
		return powerNames;
	}

	public void setPowerNames(List<RolePowerName> powerNames) {
		this.powerNames = powerNames;
	}

	@Override
	public String toString() {
		return "Role [roleId=" + roleId + ", role=" + role + ", powerNames=" + powerNames + "]";
	}




}
