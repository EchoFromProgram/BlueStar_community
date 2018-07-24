package com.bluestar.teach.entity;

import java.util.Date;

/**
 * 签到数据
 * @author 王鸿175
 *
 */
public class SignData {
	
	private Integer classId;
	//日期
	private Date date;
		
	//签到状态
	private Integer status;
		
	//迟到原因
	private String reason;
	
	//用户姓名
	private String name;
	
	//班级名
	private String className;
	
	//课程名
	private String course;

	

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	@Override
	public String toString() {
		return "SignData [classId=" + classId + ", date=" + date + ", status=" + status + ", reason=" + reason
				+ ", name=" + name + ", className=" + className + ", course=" + course + "]";
	}




	
	
}
