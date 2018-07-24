package com.bluestar.teach.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Score;
import com.bluestar.teach.enums.Statusable;
import com.bluestar.teach.enums.impl.Common;
import com.bluestar.teach.service.ScoreService;
import com.bluestar.common.utils.ValidateUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Controller
public class ScoreController {

	@Resource
	private ScoreService scoreService;
	
	//页面转跳分数管理
	@RequestMapping(path = "score_admin.do", produces = {"application/json;charset=UTF8"})
	public String scoreAdmin() {
		return "teach/score_admin.html";
	}
	
	//页面转跳学生分数
	@RequestMapping(path = "score_student.do", produces = {"application/json;charset=UTF8"})
	public String scoreStudent() {
		return "teach/score_student.html";
	}
	
	//页面转跳教师分数
	@RequestMapping(path = "score_teacher.do", produces = {"application/json;charset=UTF8"})
	public String scoreTeacher() {
		return "teach/score_teacher.html";
	}
	
	/**
	 * 学生通过他的userId来查询他的成绩
	 * 
	 * @param page 分页号
	 * @param userId 学生的userId
	 * @return 成绩列表
	 */
	@ResponseBody
	@RequestMapping(path = "student_get_score.do", produces = {"application/json;charset=UTF8"})
	public Object studentGetScore(Integer page, HttpSession session) {
		AccountDto accountDto = scoreService.getScoresByUserId(page, (Integer)((Map)session.getAttribute("user")).get("user_id"));
		return accountDto;
	}
	
	/***
	 * 教师获取成绩信息
	 * 0 0		获取全部班级全部阶段的成绩
	 * 指定 0	获指定班级全部阶段的成绩
	 * 0指定		获取指定阶段全部班级的成绩
	 * 指定 指定		获指定班级指定阶段的成绩
	 * @param page
	 * @param classId
	 * @param stage
	 * @param session
	 * @return	成绩列表
	 */
	@ResponseBody
	@RequestMapping(path = "teacher_get_score.do", produces = {"application/json;charset=UTF8"})
	public Object teacherGetScore(Integer page, Integer classId, Integer stage, HttpSession session) {
		AccountDto accountDto = null;
		Integer userId = (Integer)((Map)session.getAttribute("user")).get("user_id");
		if(classId == 0 && stage == 0) {
			//获取他的全部班级全部阶段的成绩
			accountDto = scoreService.getScoresByHisClassId(page, userId);
		}
		else if (classId != 0 && stage == 0) {
			//获指定班级全部阶段的成绩
			accountDto = scoreService.getScoresByClassId(page, classId);
		}
		else if (classId == 0 && stage != 0) {
			//获取指定阶段和他的全部班级的成绩
			accountDto = scoreService.getScoresByStatusAndHisClassId(page, userId, stage);
		}
		else if (classId != 0 && stage != 0) {
			//获指定班级指定阶段的成绩
			accountDto = scoreService.getScoresByClassIdAndStatus(page, stage, classId);
		}
		return accountDto;
	}
	
	/***
	 * 管理员获取成绩信息
	 * 0 0		获取全部班级全部阶段的成绩
	 * 指定 0	获指定班级全部阶段的成绩
	 * 0指定		获取指定阶段全部班级的成绩
	 * 指定 指定		获指定班级指定阶段的成绩
	 * @param page
	 * @param classId
	 * @param stage
	 * @return 成绩列表
	 */
	@ResponseBody
	@RequestMapping(path = "admin_get_score.do", produces = {"application/json;charset=UTF8"})
	public Object adminGetScore(Integer page, Integer classId, Integer stage) {
		AccountDto accountDto = null;

		if(classId == 0 && stage == 0) {
			//获取全部班级全部阶段的成绩
			accountDto = scoreService.getAllScores(page);
		}else if (classId != 0 && stage == 0) {
			//获指定班级全部阶段的成绩
			accountDto = scoreService.getScoresByClassId(page, classId);
		}else if (classId == 0 && stage != 0) {
			//获取指定阶段全部班级的成绩
			accountDto = scoreService.getScoreByStatus(page, stage);
		}else if (classId != 0 && stage != 0) {
			//获指定班级指定阶段的成绩
			accountDto = scoreService.getScoresByClassIdAndStatus(page, stage, classId);
		}
		return accountDto;
	}
	
	/***
	 * 分数的统计方法
	 * 0 0		获取全部班级全部阶段的成绩的统计
	 * 指定 0	获指定班级全部阶段的成绩的统计
	 * 0指定		获取指定阶段全部班级的成绩的统计
	 * 指定 指定		获指定班级指定阶段的成绩的统计
	 * @param classId
	 * @param stage
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "get_static_score.do", produces = {"application/json;charset=UTF8"})
	public Object getStaticScore(Integer classId, Integer stage, Integer userId) {
		AccountDto accountDto = null;
		if(classId == 0 && stage == 0) {
			//获取全部班级全部阶段的成绩的统计
			accountDto = scoreService.getScoreRate(null, null, userId);
		}else if (classId != 0 && stage == 0) {
			//获指定班级全部阶段的成绩的统计
			accountDto = scoreService.getScoreRate(classId, null, userId);
		}else if (classId == 0 && stage != 0) {
			//获取指定阶段全部班级的成绩的统计
			accountDto = scoreService.getScoreRate(null, stage, userId);
		}else if (classId != 0 && stage != 0) {
			//获指定班级指定阶段的成绩的统计
			accountDto = scoreService.getScoreRate(classId, stage, userId);
		}
		return accountDto;
	}
	
	@ResponseBody
	@RequestMapping(path = "admin_get_static_score.do", produces = {"application/json;charset=UTF8"})
	public Object adminGetStaticScore(Integer classId, Integer stage) {
		return getStaticScore(classId, stage, null);
	}
	
	@ResponseBody
	@RequestMapping(path = "teacher_get_static_score.do", produces = {"application/json;charset=UTF8"})
	public Object teacherGetStaticScore(Integer classId, Integer stage, HttpSession session) {
		return getStaticScore(classId, stage, (Integer)((Map)session.getAttribute("user")).get("user_id"));
	}
	/***
	 * 根据成绩id更新个人成绩
	 * 
	 * @param scoreId
	 * @param socreNum
	 * @return	操作提示
	 */
	@ResponseBody
	@RequestMapping(path = "update_score.do", produces = {"application/json;charset=UTF8"})
	public Object updateScore(@Valid Score score, BindingResult bindingResult) {
		if(bindingResult.hasErrors()) {
			return ValidateUtil.Validate(bindingResult);
		}
		AccountDto accountDto = scoreService.updateScore(score);
		return accountDto;
	}
	
	/***
	 * 根据分数id删除列表中的分数信息
	 * 
	 * @param scoreId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "delete_score.do", produces = {"application/json;charset=UTF8"})
	public Object deleteScore(Integer scoreId) {
		AccountDto accountDto = scoreService.deleteScore(scoreId);
		return accountDto;
	}
	
	/***
	 * 发布成绩
	 * 根据传进来的用户信息和成绩信息，封装n个对象到列表中执行发布成绩操作
	 * @param userIds
	 * @param scoreArr
	 * @param classId
	 * @param stage
	 * @return 操作提示
	 */
	@ResponseBody
	@RequestMapping(path = "public_score.do", produces = {"application/json;charset=UTF8"})
	public Object publicScore(String[] userIds, String[] scoreArr, Integer classId, Integer stage) {
		List<Score> scores = new ArrayList<Score>();
		Date date = new Date();
		if(userIds == null || userIds.length <= 0 || scoreArr == null || scoreArr.length <= 0) {
			return new AccountDto(Common.GET_IS_NULL);
		}
		for(int i = 0; i < scoreArr.length; i++) {
			if((Integer.parseInt(scoreArr[i])) < 0 || (Integer.parseInt(scoreArr[i])) > 100) {
				return new AccountDto(new Statusable() {
					
					@Override
					public String getInfo() {
						return "输入的成绩范围(0~100)";
					}
					
					@Override
					public int getCode() {
						return -1;
					}
				});
			}
		}
		for(int i = 0; i < userIds.length; i++) {
			Score score = new Score();
			score.setClassId(classId);
			score.setStatus(stage);
			score.setUserId(Integer.parseInt(userIds[i]));
			score.setScore(Integer.parseInt(scoreArr[i]));
			score.setDate(date);
			scores.add(score);
		}
		AccountDto accountDto = scoreService.insertScores(scores);
		return accountDto;
	}
	
	/***
	 * 发布成绩前，选择班级之后，需要显示班级的所有人的姓名
	 * 
	 * @param classId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "get_users.do", produces = {"application/json;charset=UTF8"})
	public Object getUsersByClassId(Integer classId) {
		List<Score> scores = new ArrayList<Score>();
		Date date = new Date();
		AccountDto accountDto = scoreService.getUsersByClassId(classId);
		return accountDto;
	}
	
}
