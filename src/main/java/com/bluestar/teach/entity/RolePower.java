package com.bluestar.teach.entity;
/**
 * 用户-权限类
 * @author 王鸿175
 *
 */

import java.util.List;

public class RolePower {
	
	//角色id
	private Integer roleId;
	
	//权限id集合
	private List<Integer> powerIds;

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public List<Integer> getPowerIds() {
		return powerIds;
	}

	public void setPowerIds(List<Integer> powerIds) {
		this.powerIds = powerIds;
	}

	@Override
	public String toString() {
		return "RolePower [roleId=" + roleId + ", powerIds=" + powerIds + "]";
	}

	

	

	
}
