package com.bluestar.teach.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.Quiz;
import com.bluestar.teach.entity.QuizDetail;
import com.bluestar.teach.service.QuizService;
import com.bluestar.common.utils.ListUtil;
/***
 * 
 * @author MackyHuang
 *
 */
@Controller
public class QuizController {

	@Resource
	private QuizService quizService;
	
	//页面转跳管理问卷
	@RequestMapping(path = "quiz_admin.do", produces = {"application/json;charset=UTF8"})
	public String quizAdmin() {
		return "teach/quiz_admin.html";
	}
	
	//页面转跳学生问卷
	@RequestMapping(path = "quiz_student.do", produces = {"application/json;charset=UTF8"})
	public String quizStudent() {
		return "teach/quiz_student.html";
	}
	
	//页面转跳教师问卷
	@RequestMapping(path = "quiz_teacher.do", produces = {"application/json;charset=UTF8"})
	public String quizTeacher() {
		return "teach/quiz_teacher.html";
	}
	
	/***
	 * 教师获取问卷，四种情况
	 *  0 0 	获取这个教师拥有的全部班级全部课程的问卷
	 *  0 指定   	获取这个教师这个课程的全部班级的问卷
	 *  指定 0  	获取指定班级全部课程的问卷
	 *  指定 指定		获取指定班级指定课程的问卷
	 * @param page
	 * @param classId 
	 * @param courseId
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "teacher_get_quiz.do", produces = {"application/json;charset=UTF8"})
	public Object teacherGetQuiz(Integer page, Integer classId, Integer courseId, HttpSession session) {
		AccountDto accountDto = null;
		Integer userId = (Integer)((Map)session.getAttribute("user")).get("user_id");
		if(classId == 0 && courseId == 0) {
			//获取这个教师拥有的全部班级全部课程的问卷
			accountDto = quizService.getQuizByClassIdAndCourseId(page, userId, null);
		}else if(classId == 0 && courseId != 0) {
			//获取这个教师这个课程的全部班级的问卷
			accountDto = quizService.getQuizByClassIdAndCourseId(page, userId, courseId);
		}else if(classId != 0 && courseId == 0) {
			//获取指定班级全部课程的问卷
			accountDto = quizService.getQuizsByClassAndCourse(page, classId, null);
		}else if(classId != 0 && courseId != 0) {
			//获取指定班级指定课程的问卷
			accountDto = quizService.getQuizsByClassAndCourse(page, classId, courseId);
		}
		return accountDto;
	}
	
	/***
	 * 管理员获取问卷信息
	 *  0 0 	获取全部班级全部课程的问卷
	 *  0 指定   	获取指定课程的全部班级的问卷
	 *  指定 0  	获取指定班级全部课程的问卷
	 *  指定 指定		获取指定班级指定课程的问卷
	 * @param page
	 * @param classId
	 * @param courseId
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "admin_get_quiz.do", produces = {"application/json;charset=UTF8"})
	public Object adminGetQuiz(Integer page, Integer classId, Integer courseId) {
		AccountDto accountDto = null;
		if(classId == 0 && courseId == 0) {
			//获取全部班级全部课程的问卷
			accountDto = quizService.getQuizsByClassAndCourse(page, null, null);
		}else if(classId == 0 && courseId != 0) {
			//获取指定课程的全部班级的问卷
			accountDto = quizService.getQuizsByClassAndCourse(page, null, courseId);
		}else if(classId != 0 && courseId == 0) {
			//获取指定班级全部课程的问卷
			accountDto = quizService.getQuizsByClassAndCourse(page, classId, null);
		}else if(classId != 0 && courseId != 0) {
			//获取指定班级指定课程的问卷
			accountDto = quizService.getQuizsByClassAndCourse(page, classId, courseId);
		}
		return accountDto;
	}
	
	/***
	 * 查看详细问卷
	 * @param quizId
	 * @return 问卷信息 
	 */
	@ResponseBody
	@RequestMapping(path = "get_quiz_by_id.do", produces = {"application/json;charset=UTF8"})
	public Object getQuizById(Integer quizId) {
		AccountDto accountDto = quizService.getQuizById(quizId);
		return accountDto.getData();
	}
	
	/***
	 * 学生获取他填写问卷
	 * 
	 * @param page
	 * @param session
	 * @return 问卷列表
	 */
	@ResponseBody
	@RequestMapping(path = "student_get_quiz.do", produces = {"application/json;charset=UTF8"})
	public Object studentGetQuiz(Integer page, HttpSession session) {
		AccountDto accountDto = quizService.getQuizsByUser(page, (Integer)((Map)session.getAttribute("user")).get("user_id"), null);
		return accountDto;
	}
	
	/***
	 * 填写问卷的时候需要问卷的问题
	 * @return 问卷问题列表
	 */
	@ResponseBody
	@RequestMapping(path = "get_quiz_question.do", produces = {"application/json;charset=UTF8"})
	public Object getQuizQuestion() {
		AccountDto accountDto = quizService.getQuiz();
		return accountDto;
	}
	
	/***
	 * 学生填写问卷  userId和classId都从session来获取，传入问题和问卷的Id
	 * 
	 * @param session
	 * @param answers
	 * @param quizDetailId
	 * @return 操作提示
	 */
	@ResponseBody
	@RequestMapping(path = "write_quiz.do", produces = {"application/json;charset=UTF8"})
	public Object writeQuiz(HttpSession session, String[] answers, Integer quizDetailId) {
		Quiz quiz = new Quiz();
		quiz.setUserId((Integer)((Map)session.getAttribute("user")).get("user_id"));
		quiz.setClassId(((List<Clazz>)session.getAttribute("hisClasses")).get(0).getClassId());
		quiz.setQuizDetailId(quizDetailId);
		List<String> answerList = Arrays.asList(answers);
		quiz.setAnswers(answerList);
		AccountDto accountDto = quizService.writeQuiz(quiz);
		return accountDto;
	}
	
	/***
	 * 管理员发布问卷，传入问卷的数组
	 * 
	 * @param questions
	 * @return 操作提示
	 */
	@ResponseBody
	@RequestMapping(path = "public_quiz.do", produces = {"application/json;charset=UTF8"})
	public Object publicQuiz(String[] questions) {
		List<String> questionList = Arrays.asList(questions);
		QuizDetail quizDetail = new QuizDetail();
		quizDetail.setQuestions(ListUtil.strings2QuizQuestions(questionList));
		AccountDto accountDto = quizService.publishQuiz(quizDetail);
		return accountDto;
	}
}
