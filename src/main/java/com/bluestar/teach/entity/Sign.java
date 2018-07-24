package com.bluestar.teach.entity;

import java.util.Date;

/**
 * 签到类，对应sign表
 * @author happyChicken
 *
 */
public class Sign {

	//签到id
	private Integer signId;
		
	//班级id
	private Integer classId;
		
	//用户id
	private Integer userId;
		
	//课程id
	private Integer courseId;
		
	//日期
	private Date date;
	
	//签到状态
	private Integer status;
	
	//迟到原因
	private String reason;

	public Integer getSignId() {
		return signId;
	}

	public void setSignId(Integer signId) {
		this.signId = signId;
	}

	public int getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public int getCourseId() {
		return courseId;
	}

	public void setCourseId(Integer courseId) {
		this.courseId = courseId;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	@Override
	public String toString() {
		return "Sign [signId=" + signId + ", classId=" + classId + ", userId=" + userId + ", courseId=" + courseId
				+ ", date=" + date + ", status=" + status + ", reason=" + reason + "]";
	}

	
}
