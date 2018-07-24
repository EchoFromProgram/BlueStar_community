package com.bluestar.teach.entity;
/**
 * 封装成绩数据
 * @author 王鸿175
 *
 */

import java.sql.Date;

public class ScoreData {
	
	//用户编号
	private Integer userId;
	
	//成绩编号
	private Integer scoreId;
	
	//班级id
	private Integer classId;
	
	//班级名
	private String className;
	
	//姓名
	private String name;
	
	//阶段
	private Integer status;
	
	//日期
	private Date date;
	
	//分数
	private Integer score;

	public Integer getScoreId() {
		return scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	public Integer getClassId() {
		return classId;
	}

	public void setClassId(Integer classId) {
		this.classId = classId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Override
	public String toString() {
		return "ScoreData [userId=" + userId + ", scoreId=" + scoreId + ", classId=" + classId + ", className="
				+ className + ", name=" + name + ", status=" + status + ", date=" + date + ", score=" + score + "]";
	}

	

	
}
