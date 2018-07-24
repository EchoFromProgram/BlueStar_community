package com.bluestar.teach.controller;

import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import com.bluestar.teach.constant.SessionKey;
import com.bluestar.teach.enums.impl.LoginStatus;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.bluestar.teach.dto.AccountDto;
import com.bluestar.teach.entity.User;
import com.bluestar.teach.service.AccountService;
import com.bluestar.common.service.InitService;
import com.bluestar.common.utils.ValidateUtil;

@Controller
public class LoginController {
	
	@Resource
	private AccountService accountService;
	
	@Resource
	private InitService InitService;
	
	/***
	 * 登陆验证
	 * 如果信息正确，将信息，权限和班级放入session
	 * 
	 * @param user
	 * @param session
	 * @return
	 */
	@ResponseBody
	@RequestMapping(path = "loginCheck.do", produces = {"application/json;charset=UTF8"})
	public Object loginCheck(@Valid User user, BindingResult bindingResult, HttpSession session)
	{	
		
		if(bindingResult.hasErrors()) {
			return ValidateUtil.Validate(bindingResult);
		}
			
		
		AccountDto accountDto = accountService.login(user);
		if(accountDto.getCode() == 0)
		{
			
			session.setAttribute(SessionKey.USER, ((Map)accountDto.getData()).get("user"));
			session.setAttribute("hisPowers", ((Map)accountDto.getData()).get("hisPowers"));
			session.setAttribute("hisClasses", ((Map)accountDto.getData()).get("hisClasses"));
		}
		return accountDto;
	}
	
	//转跳首页
	@RequestMapping(path = "index.do", produces = {"application/json;charset=UTF8"})
	public String loginSuccess() {
		return "teach/index.html";
	}
	
	//转跳登陆界面
	@RequestMapping(path = "login.do", produces = {"application/json;charset=UTF8"})
	public String login() {
		return "teach/Login.html";
	}

    //退出登录
    @RequestMapping(path = "logout.do", produces = {"application/json;charset=UTF8"})
    @ResponseBody
    public AccountDto logout(HttpSession session) {
        session.removeAttribute(SessionKey.USER); // 清空 session
	    return new AccountDto(LoginStatus.LOGOUT_SUCCESS);
    }
	
	//session获取信息(现在通过直接在session传入controller，无需传到前台，此方法基本不会用到)
	@ResponseBody
	@RequestMapping(path = "getSessionUser.do", produces = {"application/json;charset=UTF8"})
	public Object getSessionUser(HttpSession session) {
		return session.getAttribute(SessionKey.USER);
	}
	
	//session获取权限(现在通过直接在session传入controller，无需传到前台，此方法基本不会用到)
	@ResponseBody
	@RequestMapping(path = "getSessionHisPowers.do", produces = {"application/json;charset=UTF8"})
	public Object getSessionHisPower(HttpSession session) { 
		return session.getAttribute("hisPowers");
	}
	
	//session获取班级(现在通过直接在session传入controller，无需传到前台，此方法基本不会用到)
	@ResponseBody
	@RequestMapping(path = "getSessionHisClasses.do", produces = {"application/json;charset=UTF8"})
	public Object getSessionHisClasses(HttpSession session) {
		return session.getAttribute("hisClasses");
	}
	
	@ResponseBody
	@RequestMapping(path = "getPowerTable.do")
	public Object getPowerTable(HttpSession session) {
		//返回权限表
		//TO DO 这里需要直接返回，和上面的hisPower方法合并
		return session.getServletContext().getAttribute("powerMap");
	}
	
	//从全局获取课表
	@ResponseBody
	@RequestMapping(path = "get_courses.do", produces = {"application/json;charset=UTF8"})
	public Object getCourses(HttpSession session) {
		//返回课程表
		return session.getServletContext().getAttribute("Courses");
	}
}
