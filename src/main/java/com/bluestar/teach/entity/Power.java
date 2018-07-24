package com.bluestar.teach.entity;
/**
 * 权限类，对应power表
 * @author happyChicken
 *
 */
public class Power {
	
	// 权限id
	private Integer powerId;
	
	// 权限
	private String power;
	
	//权限名
	private String powerName;

	public Integer getPowerId() {
		return powerId;
	}

	public void setPowerId(Integer powerId) {
		this.powerId = powerId;
	}

	public String getPower() {
		return power;
	}

	public void setPower(String power) {
		this.power = power;
	}

	public String getPowerName() {
		return powerName;
	}

	public void setPowerName(String powerName) {
		this.powerName = powerName;
	}

	@Override
	public String toString() {
		return "Power [powerId=" + powerId + ", power=" + power + ", powerName=" + powerName + "]";
	}

	
	
}
