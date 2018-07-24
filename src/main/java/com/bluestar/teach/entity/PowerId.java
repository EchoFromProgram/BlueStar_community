package com.bluestar.teach.entity;
/**
 * 用户权限id
 * @author 王鸿175
 *
 */
public class PowerId {

	//权限id
	private Integer powerId;

	public PowerId(Integer powerId) {
		super();
		this.powerId = powerId;
	}

	public Integer getPowerId() {
		return powerId;
	}

	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}

	@Override
	public String toString() {
		return "PowerId [powerId=" + powerId + "]";
	}
	
	
}
