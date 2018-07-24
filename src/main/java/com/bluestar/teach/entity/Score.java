package com.bluestar.teach.entity;
/**
 * 成绩类，对应score表
 * @author happyChicken
 *
 */

import java.util.Date;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class Score {
	
	public Score(Integer classId, Integer userId, Integer status, Date date, Integer scoreNum) {
		super();
		this.classId = classId;
		this.userId = userId;
		this.status = status;
		this.date = date;
		this.score = scoreNum;
	}

	public Score() {
		super();
	}
	
	//成绩id
	private Integer scoreId;
	
	//班级id
	private Integer classId;
	
	//用户id
	private Integer userId;
	
	//课程id
	private Integer status;
	
	//日期
	private Date date;
	
	//分数
	@Min(value = 0, message = "成绩最少为0分")
	@Max(value = 100, message = "成绩不能超过100分")
	private Integer score;

	public Integer getScoreId() {
		return scoreId;
	}

	public void setScoreId(Integer scoreId) {
		this.scoreId = scoreId;
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

	@Override
	public String toString() {
		return "Score [scoreId=" + scoreId + ", classId=" + classId + ", userId=" + userId + ", status=" + status
				+ ", date=" + date + ", score=" + score + "]";
	}

	


	
	
	
}
