package com.bluestar.teach.entity;

import java.util.List;

/**
 * 用户班级类
 * @author 王鸿175
 *
 */
public class UserClass {
	
	private Integer userClassId;
	
	private Integer userId;
	
	//班级集合
	private List<Integer> classIds;

	public Integer getUserClassId() {
		return userClassId;
	}

	public void setUserClassId(Integer userClassId) {
		this.userClassId = userClassId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public List<Integer> getClassIds() {
		return classIds;
	}

	public void setClassIds(List<Integer> classIds) {
		this.classIds = classIds;
	}

	@Override
	public String toString() {
		return "UserClass [userClassId=" + userClassId + ", userId=" + userId + ", classIds=" + classIds + "]";
	}
	
	
	
	
}
