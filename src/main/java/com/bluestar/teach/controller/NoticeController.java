package com.bluestar.teach.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.Clazz;
import com.bluestar.teach.entity.Notice;
import com.bluestar.teach.entity.NoticeDetail;
import com.bluestar.teach.entity.User;
import com.bluestar.teach.service.NoticeService;

import java.util.List;
import java.util.Map;

@Controller
public class NoticeController {
	
	@Resource
	private NoticeService noticeService;
	
	//页面转跳通知管理
	@RequestMapping(path = "notice_admin.do", produces = {"application/json;charset=UTF8"})
	public String noticeAdmin() {
		return "teach/notice_admin.html";
	}
	
	//页面转跳客户通知
	@RequestMapping(path = "notice_student.do", produces = {"application/json;charset=UTF8"})
	public String noticeStudent() {
		return "teach/notice_student.html";
	}
	
	//页面转跳员工通知
	@RequestMapping(path = "notice_teacher.do", produces = {"application/json;charset=UTF8"})
	public String noticeTeacher() {
		return "teach/notice_teacher.html";
	}
	
	/***
	 * 教师级别身份获取所有通知
	 * 
	 * @param page
	 * @param session
	 * @return 通知列表
	 */
	@ResponseBody
	@RequestMapping(path = "teacher_get_all_notice.do", produces = {"application/json;charset=UTF8"})
	public Object getTeacherAllNotice(Integer page, HttpSession session) {
		User user = new User();
		user.setUserId((Integer)((Map)session.getAttribute("user")).get("user_id"));
		AccountDto accountDto = noticeService.getNotices(page, user);
		return accountDto;
	}
	
	/***
	 * //客户身份获取所有通知
	 * 
	 * @param page
	 * @param session
	 * @return 通知列表
	 */
	@ResponseBody
	@RequestMapping(path = "student_get_all_notice.do", produces = {"application/json;charset=UTF8"})
	public Object getStudentAllNotice(Integer page, HttpSession session) {
		AccountDto accountDto = noticeService.getNotices(page, ((List<Clazz>)session.getAttribute("hisClasses")).get(0).getClassId());
		return accountDto;
	}
	
	/***
	 * 管理通知
	 * 
	 * @param page
	 * @return 通知列表
	 */
	@ResponseBody
	@RequestMapping(path = "admin_get_all_notice.do", produces = {"application/json;charset=UTF8"})
	public Object getAdminAllNotice(Integer page) {
		AccountDto accountDto = noticeService.getAllNoticeDetails(page);
		return accountDto;
	}
	
	/**
	 * 提供给教师和管理员，传相应的通知id然后删除通知
	 * 
	 * @param noticeId 
	 * @return accountDto
	 */
	@ResponseBody
	@RequestMapping(path = "delete_notice.do", produces = {"application/json;charset=UTF8"})
	public Object deleteNotice(Integer noticeId) {
		AccountDto accountDto = noticeService.deleteNoticeDetailAndNotice(noticeId);
		return accountDto;
	}
	
	/***
	 * 根据通知的id更新通知
	 * 
	 * @param noticeDetail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "update_notice.do", produces = {"application/json;charset=UTF8"})
	public Object updateNotice(NoticeDetail noticeDetail) {
		AccountDto accountDto = noticeService.updateNoticeDetailByNoticeDetailId(noticeDetail);
		return accountDto;
	}
	
	/***
	 * 查看一条通知
	 * 
	 * @param noticeId
	 * @return 通知详细
	 */
	@ResponseBody
	@RequestMapping(path = "get_one_notice.do", produces = {"application/json;charset=UTF8"})
	public Object getOneNotice(Integer noticeId) {
		AccountDto accountDto = noticeService.getNoticeDetail(noticeId);
		return accountDto;
	}
	
	/***
	 * 添加通知
	 * 
	 * @param classId
	 * @param title
	 * @param content
	 * @param session
	 * @return 操作提示
	 */
	@ResponseBody
	@RequestMapping(path = "add_notice.do", produces = {"application/json;charset=UTF8"})
	public Object addNotice(Integer classId, String title, String content, HttpSession session) {
		Notice notice = new Notice();
		notice.setClassId(classId);
		notice.setUserId((Integer)((Map)session.getAttribute("user")).get("user_id"));
		NoticeDetail noticeDetail = new NoticeDetail();
		noticeDetail.setTitle(title);
		noticeDetail.setContent(content);
		AccountDto accountDto = noticeService.insertNotice(notice, noticeDetail);
		
		return accountDto;
	}
}
