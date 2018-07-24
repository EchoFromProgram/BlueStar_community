package com.bluestar.teach.entity;
/**
 * 角色名和他所拥有的权限名
 * @author 王鸿175
 *
 */

import java.util.List;

public class RolePowerName {
	
	// 权限名
	private String powerName;
	
	private Integer powerId;
	
	public String getPowerName() {
		return powerName;
	}


	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}


	public Integer getPowerId() {
		return powerId;
	}


	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}


	@Override
	public String toString() {
		return "RolePowerName [powerName=" + powerName + ", powerId=" + powerId + "]";
	}
}
