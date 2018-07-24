package com.bluestar.teach.controller;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.Sign;
import com.bluestar.teach.entity.User;
import com.bluestar.teach.service.ClassService;
import com.bluestar.teach.service.SignService;
import com.bluestar.common.utils.ContextUtil;

@Controller
public class SignController {
	
	@Resource
	private SignService signService;
	
	@Resource
	private ClassService ClassService;
	
	//页面转跳管理签到
	@RequestMapping(path = "sign_admin.do", produces = {"application/json;charset=UTF8"})
	public String signAdmin() {
		return "teach/sign_admin.html";
	}
	
	//页面转跳学生签到
	@RequestMapping(path = "sign_student.do", produces = {"application/json;charset=UTF8"})
	public String signStudent() {
		return "teach/sign_student.html";
	}
	
	//页面转跳学生签到
	@RequestMapping(path = "sign_teacher.do", produces = {"application/json;charset=UTF8"})
	public String signTeacher() {
		return "teach/sign_teacher.html";
	}
	
	/***
	 * 学生获取签到记录 根据他自己的id
	 * 
	 * @param page
	 * @param session
	 * @return 签到信息列表
	 */
	@ResponseBody
	@RequestMapping(path = "init_sign_student.do", produces = {"application/json;charset=UTF8"})
	public Object initSignStudent(Integer page, HttpSession session) {
		User user = new User();
		user.setUserId((Integer)((Map)session.getAttribute("user")).get("user_id"));
		AccountDto<List<Sign>> accountDto = signService.getSignsByUser(page, user);
		return accountDto;
	}
	
	/***
	 * 教师发布签到码
	 * 将系统随机生成的签到码加入到context中
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "get_sign_code.do", produces = {"application/json;charset=UTF8"})
	public Object getSignCode(HttpSession session) {
		Integer code = signService.getSignCode();
		ContextUtil.putSignCode((Integer)((Map)session.getAttribute("user")).get("user_id"), code);
		return code;
	}
	
	/***
	 * 根据班级获得这个班级的课程
	 * 
	 * @param classId
	 * @return 课程的信息
	 */
	@ResponseBody
	@RequestMapping(path = "get_course_by_class.do", produces = {"application/json;charset=UTF8"})
	public Object getCourseByClass(Integer classId) {
		AccountDto accountDto = ClassService.getCourseByClassId(classId);
		return accountDto;
	}
	
	/***
	 * 管理员获得签到信息
	 * 0 0		获取全部班级全部课程的签到信息
	 * 指定 0 	获取指定班级全部课程的签到信息
	 * 0 指定	获取指定课程全部班级的签到信息
	 * 指定 指定   获取指定班级指定课程的签到信息
	 * @param page
	 * @param classId
	 * @param courseId
	 * @return 签到信息列表
	 */
	@ResponseBody
	@RequestMapping(path = "admin_get_signs.do", produces = {"application/json;charset=UTF8"})
	public Object adminGetSigns(Integer page, Integer classId, Integer courseId) {
		AccountDto accountDto = null;
		Clazz clazz = new Clazz();
		clazz.setClassId(classId);
		if(classId == 0 && courseId == 0) {
			//获取全部班级全部课程的签到信息
			accountDto = signService.getSigns(page);
		}
		else if (classId != 0 && courseId == 0) {
			//获取指定班级全部课程的签到信息
			accountDto = signService.getClassSigns(page, clazz);
		}
		else if (classId == 0 && courseId != 0) {
			//获取指定课程全部班级的签到信息
			accountDto = signService.getStudentSignsByCourseId(page, courseId);
		}
		else if (classId != 0 && courseId != 0) {
			//获取指定班级指定课程的签到信息
			accountDto = signService.getSignsByCouseIdAndClassId(page, courseId, classId);
		}
		return accountDto;
	}
	
	/***
	 * 教师获取签到信息
	 * 0 0		获取全部班级全部课程的签到信息
	 * 指定 0 	获取指定班级全部课程的签到信息
	 * 0 指定	获取指定课程全部班级的签到信息
	 * 指定 指定   获取指定班级指定课程的签到信息
	 * @param page
	 * @param classId
	 * @param courseId
	 * @param session
	 * @return 签到信息列表
	 */
	@ResponseBody
	@RequestMapping(path = "teacher_get_signs.do", produces = {"application/json;charset=UTF8"})
	public Object teacherGetSigns(Integer page, Integer classId, Integer courseId, HttpSession session) {
		AccountDto accountDto = null;
		Integer userId = (Integer)((Map)session.getAttribute("user")).get("user_id");
		Clazz clazz = new Clazz();
		clazz.setClassId(classId);
		if(classId == 0 && courseId == 0) {
			//获取教师拥有的全部班级全部课程的签到信息
			accountDto = signService.getSignsByUserId(page, userId);
		}
		else if (classId != 0 && courseId == 0) {
			//获取指定班级全部课程的签到信息
			accountDto = signService.getStudentSignsByClass(page, clazz);
		}
		else if (classId == 0 && courseId != 0) {
			//获取指定课程和他的全部班级的签到信息
			accountDto = signService.getSignsByCourseIdAndHisClassId(page, userId, courseId);
		}
		else if (classId != 0 && courseId != 0) {
			//获取指定班级指定课程的签到信息
			accountDto = signService.getSignsByCouseIdAndClassId(page, courseId, classId);
		}
		return accountDto;
	}
	
	@ResponseBody
	@RequestMapping(path = "get_static_sign.do", produces = {"application/json;charset=UTF8"})
	public Object getStaticSign(Integer classId, Integer courseId) {
		AccountDto accountDto = signService.getSignRate(classId, courseId);
		return accountDto;
	}
	
	/***
	 * 学生签到方法
	 * 匹配输入的code和系统上的code是不是相同来判断签到，迟到还必须填写迟到原因
	 * @param session 
	 * @param inputCode
	 * @param reason
	 * @return 操作结果
	 */
	@ResponseBody
	@RequestMapping(path = "student_sign.do", produces = {"application/json;charset=UTF8"})
	public Object studentSign(HttpSession session, Integer inputCode, String reason) {
		//TODO 如何让学生签到的时候获取到签到码，然后，关联到教师签到的时候怎么确定是哪个班级，若确定，就可以使用班级id提到这里的userId 
		Integer realCode = ContextUtil.getSignCode(1);
		Integer classId = ((List<Clazz>)session.getAttribute("hisClasses")).get(0).getClassId();
		User user = new User();
		user.setUserId((Integer)((Map)session.getAttribute("user")).get("user_id"));
		AccountDto accountDto = signService.sign(user, inputCode, realCode, classId, reason);
		return accountDto;
	}
	
	/***
	 * 教师签到
	 * 迟到需要填写原因
	 * @param session
	 * @param reason
	 * @return 操作结果
	 */
	@ResponseBody
	@RequestMapping(path = "teacher_sign.do", produces = {"application/json;charset=UTF8"})
	public Object teacherSign(HttpSession session, String reason) {
		Integer classId = ((List<Clazz>)session.getAttribute("hisClasses")).get(0).getClassId();
		User user = new User();
		user.setUserId((Integer)((Map)session.getAttribute("user")).get("user_id"));
		AccountDto accountDto = signService.sign(user, classId, reason);
		return accountDto;
	}
}
